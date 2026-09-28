package at.technikum.repository.util;

public enum MediaSortField {
    TITLE("m.title"),
    RELEASE_YEAR("m.release_date"),
    AVERAGE_SCORE("average_score");

    private final String column;

    MediaSortField(String column) {
        this.column = column;
    }

    public String column() {
        return column;
    }

}
