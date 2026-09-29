package at.technikum.repository.interfaces;

import at.technikum.dto.response.LeaderboardEntry;
import at.technikum.dto.response.UserStatistic;

import java.util.List;
import java.util.UUID;

public interface IStatisticRepo {
    UserStatistic getUserStatistic(UUID userId);
    List<LeaderboardEntry> getLeaderboard();
}
