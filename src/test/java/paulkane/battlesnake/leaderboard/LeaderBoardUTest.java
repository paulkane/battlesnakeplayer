package paulkane.battlesnake.leaderboard;

import org.junit.Test;
import paulkane.battlesnake.model.Death;
import paulkane.battlesnake.model.Snake;
import paulkane.battlesnake.model.gamestatus.GameStatus;
import paulkane.battlesnake.model.gamestatus.LastFrame;

import java.util.List;

public class LeaderBoardUTest {

    @Test
    public void buildLeaderBoard() {
        List<Snake> snakes = List.of(
            snake("one", null),
            snake("two", "wall"),
            snake("three", "wall"));
        LastFrame lastFrame = new LastFrame();
        lastFrame.setSnakes(snakes);
        GameStatus gameStatus = new GameStatus();
        gameStatus.setLastFrame(lastFrame);

        LeaderBoard.addResult(gameStatus);
        LeaderBoard.addResult(gameStatus);
        LeaderBoard.printResults();
    }

    private Snake snake(String name, String deathReason) {
        Snake snake = new Snake();
        snake.setName(name);

        if (deathReason != null) {
            Death death = new Death();
            death.setCause(deathReason);
            snake.setDeath(death);
        }

        return snake;
    }
}