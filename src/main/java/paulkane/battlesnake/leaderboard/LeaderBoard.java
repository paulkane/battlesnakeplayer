package paulkane.battlesnake.leaderboard;

import org.springframework.beans.factory.annotation.Value;
import paulkane.battlesnake.model.Snake;
import paulkane.battlesnake.model.gamestatus.GameStatus;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/*
 place results in a queue. Was worried about race conditions updating the `Result` in the map.
 */
public class LeaderBoard {

    private static final HashMap<String, Result> RESULTS = new HashMap<>();
    private static final LinkedBlockingQueue<GameStatus> queue = new LinkedBlockingQueue<>();
    private static int printResultsEvery = 10;
    private static int maxSnakeNameSize = 0;

    public LeaderBoard(@Value("${battlesnake.print.results.every:10}") int printResultsEvery) {
        LeaderBoard.printResultsEvery = printResultsEvery;
    }

    static {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Adder());
    }

    public static void addResult(GameStatus gameStatus) {
        System.out.print(".");
        queue.add(gameStatus);
    }

    public static void printResults() {
        while (queue.size() > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignore) {
            }
        }
        printResultsToScreen();
    }

    private static void printResultsToScreen() {
        System.out.println();
        System.out.println("Results");
        for (Result result : RESULTS.values()) {
            System.out.printf("%" + maxSnakeNameSize + "s [%3s/%3s]%n", result.getSnakeName(), result.getGameWon(),
                result.getGamePlayed());
        }
    }

    static class Adder implements Runnable {
        private int count;

        @Override
        public void run() {
            GameStatus gameStatus;
            try {
                while (!Thread.interrupted()) {
                    gameStatus = queue.take();
                    for (Snake snake : gameStatus.getLastFrame().getSnakes()) {
                        Result result =
                            RESULTS.getOrDefault(snake.getName(), Result.builder().snakeName(snake.getName()).build());
                        if (snake.getDeath() == null) {
                            result.wonGame();
                        }
                        result.played();
                        maxSnakeNameSize = Math.max(maxSnakeNameSize, snake.getName().length());
                        RESULTS.put(snake.getName(), result);
                    }
                    if (++count % printResultsEvery == 0) {
                        printResultsToScreen();
                    }
                }
            } catch (InterruptedException ignore) {

            }
        }
    }
}
