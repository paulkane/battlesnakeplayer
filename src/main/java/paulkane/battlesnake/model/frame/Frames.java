package paulkane.battlesnake.model.frame;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Frames {
    @JsonProperty("Frames")
    private List<Frame> frames;
}
