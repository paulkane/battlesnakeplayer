package paulkane.battlesnake.model.frame;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import paulkane.battlesnake.model.Snake;

import java.util.List;

@Data
public class Frame {
    @JsonProperty("Turn")
    private int turn;
    @JsonProperty("Snakes")
    private List<Snake> snakes;
}
