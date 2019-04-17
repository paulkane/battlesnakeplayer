package paulkane.battlesnake.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import paulkane.battlesnake.model.Board;
import paulkane.battlesnake.model.GameId;
import paulkane.battlesnake.model.Snake;
import paulkane.battlesnake.model.frame.Frames;
import paulkane.battlesnake.model.gamestatus.GameStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
        );
    }

    public static Board getBoard() throws IOException, URISyntaxException {
        return readBoard("board.json");
    }

    public static Board readBoard(String jsonFile) throws IOException, URISyntaxException {
        return OBJECT_MAPPER.readValue(read(jsonFile), Board.class);
    }

    public static List<Snake> getSnakes() throws IOException, URISyntaxException {
        return readSnakes("snakes.json");
    }

    public static List<Snake> readSnakes(String jsonFile) throws IOException, URISyntaxException {
        return OBJECT_MAPPER.readValue(read(jsonFile), new TypeReference<List<Snake>>() {
        });
    }

    public static GameId readGameId(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, GameId.class);
    }

    public static Frames readFrames(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, Frames.class);
    }

    public static GameStatus readGameStatus(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, GameStatus.class);
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    static String read(String filename) throws URISyntaxException, IOException {
        Path classPathResource = Paths.get(JsonUtils.class.getClassLoader().getResource(filename).toURI());
        return new String(Files.readAllBytes(classPathResource));
    }
}
