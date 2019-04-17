package paulkane.battlesnake.leaderboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
    private String snakeName;
    private int gamePlayed;
    private int gameWon;

    public void wonGame() {
        gameWon ++;
    }

    public void played() {
        gamePlayed++;
    }
}
