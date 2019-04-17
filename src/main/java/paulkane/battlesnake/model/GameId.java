package paulkane.battlesnake.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GameId {
    @JsonProperty("ID")
    private String id;
}
