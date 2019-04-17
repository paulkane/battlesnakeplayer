package paulkane.battlesnake.model.gamestatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import paulkane.battlesnake.model.Snake;

import java.util.List;

@Data
public class LastFrame {
    @JsonProperty("Turn")
    private int turn;
    @JsonProperty("Snakes")
    private List<Snake> snakes;
}
