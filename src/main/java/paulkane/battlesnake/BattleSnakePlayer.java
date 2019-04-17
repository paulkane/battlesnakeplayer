package paulkane.battlesnake;

import paulkane.battlesnake.leaderboard.LeaderBoard;
import paulkane.battlesnake.model.Board;
import paulkane.battlesnake.model.GameId;
import paulkane.battlesnake.model.frame.Frames;
import paulkane.battlesnake.model.gamestatus.GameStatus;

import java.util.concurrent.Callable;

public class BattleSnakePlayer implements Callable {
    private final BattleSnakeClient battleSnakeClient;
    private final Board board;
    private final int gamesToPlay;

    public BattleSnakePlayer(BattleSnakeClient battleSnakeClient, Board board, int gamesToPlay) {
        this.battleSnakeClient = battleSnakeClient;
        this.board = board;
        this.gamesToPlay = gamesToPlay;
    }

    @Override
    public Object call() throws Exception {
        try {
            for (int numberOfGames = 0; numberOfGames < gamesToPlay; numberOfGames++) {
                GameId gameId = battleSnakeClient.games(board);
                battleSnakeClient.start(gameId);

                GameStatus gameStatus;
                do {
                    Frames frames = battleSnakeClient.frames(gameId);
                    gameStatus = battleSnakeClient.gameStatus(gameId);
                } while (gameStatus.getGame().getStatus().equals("running"));

                LeaderBoard.addResult(gameStatus);
            }
        } catch (Exception ignore) {

        }
        return null;
    }
}
