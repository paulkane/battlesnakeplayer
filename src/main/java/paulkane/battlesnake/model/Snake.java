package paulkane.battlesnake.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Snake {
    @JsonProperty("Name")
    String name;
    @JsonProperty("URL")
    String url;
    @JsonProperty("Death")
    Death death;
}
