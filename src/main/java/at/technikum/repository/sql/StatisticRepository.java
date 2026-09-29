package at.technikum.repository.sql;

import at.technikum.dto.response.LeaderboardEntry;
import at.technikum.dto.response.UserStatistic;
import at.technikum.repository.interfaces.IStatisticRepo;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

public class StatisticRepository implements IStatisticRepo {
    private final DataSource ds;

    public StatisticRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public UserStatistic getUserStatistic(UUID userId) {
        return null;
    }

    @Override
    public List<LeaderboardEntry> getLeaderboard() {
        return List.of();
    }
}
