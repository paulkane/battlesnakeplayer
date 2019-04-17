package paulkane.battlesnake.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Board {
    private int width;
    private int height;
    private int food;
    @JsonProperty(value = "MaxTurnsToNextFoodSpawn")
    private int maxTurnsToNextFoodSpawn;
    private List<Snake> snakes;
}
