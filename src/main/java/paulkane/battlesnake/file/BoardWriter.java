package paulkane.battlesnake.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import paulkane.battlesnake.model.GameId;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Service
@Slf4j
public class BoardWriter {
    private final String boardURI;
    private final String engineURI;
    private final Path writeTo;

    public BoardWriter(@Value("${battlesnake.board.url}") String boardURI,
                       @Value("${battlesnake.engine.url}") String engineURI,
                       @Value("${battlesnake.output.played.games.file}") String playedGamesFile) {
        this.boardURI = boardURI;
        this.engineURI = engineURI;
        this.writeTo = Paths.get(playedGamesFile);
        log.info("Writing results to {}", writeTo.getFileName());
    }

    public void write(GameId gameId) {
        try {
            String data = String.format("%s/?engine=%s&game=%s%n", boardURI, engineURI, gameId.getId());
            Files.write(writeTo, data.getBytes(), APPEND, CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
