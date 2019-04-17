package paulkane.battlesnake.model.gamestatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Game {
    /*
        "ID": "361dcb2f-412b-416b-8640-0b77302b0a7f",
            "Status": "running",
            "Width": 15,
            "Height": 15,
            "SnakeTimeout": 500,
            "Mode": "multi-player",
            "MaxTurnsToNextFoodSpawn": 0,
            "TurnsSinceLastFoodSpawn": 0
    */
    @JsonProperty("ID")
    private String id;
    @JsonProperty("Status")
    private String status;
}
