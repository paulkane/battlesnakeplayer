package paulkane.battlesnake.model.gamestatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GameStatus {
    @JsonProperty("Game")
    private Game game;
    @JsonProperty("LastFrame")
    private LastFrame lastFrame;
}
