package paulkane.battlesnake.leaderboard;

import paulkane.battlesnake.model.Snake;
import paulkane.battlesnake.model.gamestatus.GameStatus;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LeaderBoard {

    private static final HashMap<String, Result> RESULTS = new HashMap<>();
    private static ConcurrentLinkedQueue<GameStatus> queue = new ConcurrentLinkedQueue<>();

    static {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Adder());
    }

    public static void addResult(GameStatus gameStatus) {
        System.out.print(".");
        queue.add(gameStatus);
    }

    public static void printResults() {
        System.out.println();
        System.out.println("Results");
        for (Result result : RESULTS.values()) {
            System.out.printf("%20s [%s/%s]%n", result.getSnakeName(), result.getGameWon(), result.getGamePlayed());
        }
    }

    static class Adder implements Runnable {

        @Override
        public void run() {
            GameStatus gameStatus;
            while (!Thread.interrupted()) {
                gameStatus = queue.poll();
                if (gameStatus != null) {
                    for (Snake snake : gameStatus.getLastFrame().getSnakes()) {
                        Result result =
                            RESULTS.getOrDefault(snake.getName(), Result.builder().snakeName(snake.getName()).build());
                        if (snake.getDeath() == null) {
                            result.wonGame();
                        }
                        result.played();
                        RESULTS.put(snake.getName(), result);
                    }
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignore) {

                    }
                }
            }
        }
    }
}
