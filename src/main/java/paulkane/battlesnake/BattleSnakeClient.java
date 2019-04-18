package paulkane.battlesnake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import paulkane.battlesnake.json.JsonUtils;
import paulkane.battlesnake.model.Board;
import paulkane.battlesnake.model.GameId;
import paulkane.battlesnake.model.frame.Frames;
import paulkane.battlesnake.model.gamestatus.GameStatus;

import java.io.IOException;

/**
 * Need to get response as String; the response type is text/plain not application/json *sigh*
 */
@Service
public class BattleSnakeClient {
    private static final String GAMES = "/games";
    private final RestTemplate restTemplate;

    public BattleSnakeClient(
        @Value("${battlesnake.engine.url}") String rootUri,
        RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
            .rootUri(rootUri)
            .build();
    }

    public void start(GameId gameId) {
        String startUri = String.format("%s/%s/start", GAMES, gameId.getId());
        restTemplate.postForEntity(startUri, "", String.class);
    }

    public GameId games(Board board) throws IOException {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(GAMES, board, String.class);
        return JsonUtils.readGameId(stringResponseEntity.getBody());
    }

    public Frames frames(GameId gameId) throws IOException {
        String framesUri = String.format("%s/%s/frames", GAMES, gameId.getId());

        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity(framesUri, String.class);

        return JsonUtils.readFrames(stringResponseEntity.getBody());
    }

    public GameStatus gameStatus(GameId gameId) throws IOException {
        String statusUri = String.format("%s/%s", GAMES, gameId.getId());
        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity(statusUri, String.class);
        return JsonUtils.readGameStatus(stringResponseEntity.getBody());
    }
}
