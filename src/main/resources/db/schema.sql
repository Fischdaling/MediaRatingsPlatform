-- =====================================================================
-- Media Ratings Platform (MRP) - database init script (PostgreSQL)
--
-- Idempotent: safe to run on every server start (IF NOT EXISTS everywhere).
-- NOTE: SchemaInitializer splits this file on the semicolon character,
--       so never put that character inside comments, strings or
--       function bodies in this file.
-- Enum values match the Java enums MediaType and Genre (Enum.name()).
-- =====================================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ---------------------------------------------------------------------
-- Users
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id              UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    username        VARCHAR(50)  NOT NULL UNIQUE,
    password_hash   VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT chk_username_not_blank CHECK (length(trim(username)) > 0)
    );

-- ---------------------------------------------------------------------
-- Media entries (movie / series / game in one table, discriminated by media_type)
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS media_entries (
    id              UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    creator_id      UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title           VARCHAR(255) NOT NULL,
    description     TEXT,
    media_type      VARCHAR(10)  NOT NULL,
    release_date    DATE,
    age_restriction INTEGER      NOT NULL DEFAULT 0,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT chk_media_type       CHECK (media_type IN ('Movie', 'Series', 'Game')),
    CONSTRAINT chk_age_restriction  CHECK (age_restriction BETWEEN 0 AND 21),
    CONSTRAINT chk_title_not_blank  CHECK (length(trim(title)) > 0)
    );

CREATE INDEX IF NOT EXISTS idx_media_creator      ON media_entries(creator_id);
CREATE INDEX IF NOT EXISTS idx_media_type         ON media_entries(media_type);
CREATE INDEX IF NOT EXISTS idx_media_release_date ON media_entries(release_date);
CREATE INDEX IF NOT EXISTS idx_media_title_lower  ON media_entries(lower(title));

-- ---------------------------------------------------------------------
-- Genres of a media entry (n:m with the Genre enum)
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS media_genres (
    media_id        UUID         NOT NULL REFERENCES media_entries(id) ON DELETE CASCADE,
    genre           VARCHAR(20)  NOT NULL,
    PRIMARY KEY (media_id, genre),
    CONSTRAINT chk_genre CHECK (genre IN (
                                'Action', 'Adventure', 'Animation', 'Comedy', 'Crime',
                                'Documentary', 'Drama', 'Fantasy', 'Horror', 'Mystery',
                                'Romance', 'SciFi', 'Thriller', 'RPG', 'Strategy',
                                'Simulation', 'Sports', 'Puzzle'
                                         ))
    );

CREATE INDEX IF NOT EXISTS idx_media_genres_genre ON media_genres(genre);

-- ---------------------------------------------------------------------
-- Ratings: one per user per media, hidden until confirmed by the creator
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ratings (
    id              UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    media_id        UUID         NOT NULL REFERENCES media_entries(id) ON DELETE CASCADE,
    stars           SMALLINT     NOT NULL,
    comment         TEXT,
    is_hidden       BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT chk_stars              CHECK (stars BETWEEN 1 AND 5),
    CONSTRAINT uq_rating_user_media   UNIQUE (user_id, media_id)
    );

CREATE INDEX IF NOT EXISTS idx_ratings_media ON ratings(media_id);
CREATE INDEX IF NOT EXISTS idx_ratings_user  ON ratings(user_id);

-- ---------------------------------------------------------------------
-- Rating likes: one like per user per rating
-- (not liking your own rating is enforced in Rating.addLike())
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS rating_likes (
    rating_id       UUID         NOT NULL REFERENCES ratings(id) ON DELETE CASCADE,
    user_id         UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    PRIMARY KEY (rating_id, user_id)
    );

CREATE INDEX IF NOT EXISTS idx_rating_likes_user ON rating_likes(user_id);

-- ---------------------------------------------------------------------
-- Favorites: a user can mark a media entry once
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS favorites (
    user_id         UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    media_id        UUID         NOT NULL REFERENCES media_entries(id) ON DELETE CASCADE,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, media_id)
    );

CREATE INDEX IF NOT EXISTS idx_favorites_media ON favorites(media_id);

-- ---------------------------------------------------------------------
-- View: media with computed average score (visible ratings only),
-- rating count, favorite count and genres as array.
-- Use this for search, filtering (min rating) and sorting.
-- ---------------------------------------------------------------------
CREATE OR REPLACE VIEW media_overview AS
SELECT
    m.id,
    m.creator_id,
    m.title,
    m.description,
    m.media_type,
    m.release_date,
    m.age_restriction,
    m.created_at,
    m.updated_at,
    COALESCE(r.avg_score, 0)     AS average_score,
    COALESCE(r.rating_count, 0)  AS rating_count,
    COALESCE(f.fav_count, 0)     AS favorite_count,
    COALESCE(g.genres, ARRAY[]::VARCHAR[]) AS genres
FROM media_entries m
         LEFT JOIN (
    SELECT media_id,
           ROUND(AVG(stars)::NUMERIC, 2) AS avg_score,
           COUNT(*)                      AS rating_count
    FROM ratings
    WHERE is_hidden = FALSE
    GROUP BY media_id
) r ON r.media_id = m.id
         LEFT JOIN (
    SELECT media_id, COUNT(*) AS fav_count
    FROM favorites
    GROUP BY media_id
) f ON f.media_id = m.id
         LEFT JOIN (
    SELECT media_id, ARRAY_AGG(genre ORDER BY genre) AS genres
    FROM media_genres
    GROUP BY media_id
) g ON g.media_id = m.id;

-- ---------------------------------------------------------------------
-- View: public leaderboard, sorted by number of ratings
-- ---------------------------------------------------------------------
CREATE OR REPLACE VIEW leaderboard AS
SELECT
    RANK() OVER (ORDER BY COUNT(r.id) DESC) AS rank,
    u.id        AS user_id,
    u.username,
    COUNT(r.id) AS rating_count
FROM users u
         LEFT JOIN ratings r ON r.user_id = u.id
GROUP BY u.id, u.username
ORDER BY rating_count DESC, u.username ASC