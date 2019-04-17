package paulkane.battlesnake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import paulkane.battlesnake.json.JsonUtils;
import paulkane.battlesnake.leaderboard.LeaderBoard;
import paulkane.battlesnake.model.Board;
import paulkane.battlesnake.model.Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {
    private static BattleSnakeClient battleSnakeClient;
    private static int gamesToPlay;
    private static int maxNumberOfSnakes;
    private static Random random = new Random();

    public Application(BattleSnakeClient battleSnakeClient,
                       @Value("${battlesnake.max.snakes}") int maxNumberOfSnakes,
                       @Value("${battlesnake.number.battles}") int gamesToPlay

    ) {
        Application.battleSnakeClient = battleSnakeClient;
        Application.maxNumberOfSnakes = maxNumberOfSnakes;
        Application.gamesToPlay = gamesToPlay;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService completionService = new ExecutorCompletionService(executorService);
        List<Snake> allSnakes = JsonUtils.getSnakes();

        int numberOfClients = 0;
        for (int numberOfGames = 0; numberOfGames < allSnakes.size(); numberOfGames++) {
            Board board = JsonUtils.getBoard();
            List<Snake> snakes = new ArrayList<>(allSnakes);
            List<Snake> snakesToPlay = new ArrayList<>();

            if (allSnakes.size() < maxNumberOfSnakes) {
                snakesToPlay = allSnakes;
            } else for (int getSnake = 0; getSnake < maxNumberOfSnakes; getSnake++) {
                snakesToPlay.add(snakes.remove(random.nextInt(snakes.size())));
            }

            board.setSnakes(snakesToPlay);

//            System.out.printf("Game %s [%s]%n", numberOfGames, JsonUtils.toJson(board));
            BattleSnakePlayer battleSnakePlayer = new BattleSnakePlayer(battleSnakeClient, board, gamesToPlay);
            completionService.submit(battleSnakePlayer);
            numberOfClients++;
        }

        for (int numberOfCompletions = 0; numberOfCompletions < numberOfClients; numberOfCompletions++) {
            completionService.take();
        }

        Thread.sleep(1000);
        LeaderBoard.printResults();
        System.exit(0);
    }
}
