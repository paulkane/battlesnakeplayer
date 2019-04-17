package paulkane.battlesnake.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
//{"Cause":"head-collision","Turn":74}
@Data
public class Death {
    @JsonProperty("Cause")
    private String cause;
    @JsonProperty("Turn")
    private int turn;
}
