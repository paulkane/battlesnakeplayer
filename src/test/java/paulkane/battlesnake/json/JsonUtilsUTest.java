package paulkane.battlesnake.json;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import paulkane.battlesnake.model.Board;
import paulkane.battlesnake.model.GameId;
import paulkane.battlesnake.model.Snake;
import paulkane.battlesnake.model.frame.Frames;
import paulkane.battlesnake.model.gamestatus.GameStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilsUTest {

    @Test
    public void testReadBoard() throws IOException, URISyntaxException {
        Board board = JsonUtils.getBoard();
        assertThat(board.getFood()).isEqualTo(5);
        assertThat(board.getHeight()).isEqualTo(15);
        assertThat(board.getWidth()).isEqualTo(15);
    }

    @Test
    public void testReadSnakes() throws IOException, URISyntaxException {
        List<Snake> snakes = JsonUtils.getSnakes();
        assertThat(snakes.size()).isEqualTo(3);
    }

    @Test
    public void testReadGameId() throws Exception {
        GameId gameId = JsonUtils.readGameId("{\"ID\":\"361dcb2f-412b-416b-8640-0b77302b0a7f\"}");
        assertThat(gameId.getId()).isEqualTo("361dcb2f-412b-416b-8640-0b77302b0a7f");
    }

    @Test
    public void testReadFrames() throws Exception {
        String framesJson = JsonUtils.read("frames.json");
        Frames frames = JsonUtils.readFrames(framesJson);
        assertThat(frames).isNotNull();
        assertThat(frames.getFrames().get(0).getSnakes().get(0).getDeath()).isNull();
    }

    @Test
    public void testReadGameStatus() throws Exception {
        String gameStatusJson = JsonUtils.read("status-playing.json");
        GameStatus gameStatus = JsonUtils.readGameStatus(gameStatusJson);
        assertThat(gameStatus.getLastFrame().getSnakes().get(0).getDeath()).isNull();
    }

    @Test
    public void testReadGameStatusProcessDeath() throws Exception {
        GameStatus gameStatus = JsonUtils.readGameStatus(
            "{\"Game\":{\"ID\":\"94476bc6-49d0-47fc-85a6-dfbc685f5e85\",\"Status\":\"running\",\"Width\":15,\"Height\":15,\"SnakeTimeout\":500,\"Mode\":\"multi-player\",\"MaxTurnsToNextFoodSpawn\":0,\"TurnsSinceLastFoodSpawn\":0},\"LastFrame\":{\"Turn\":75,\"Food\":[{\"X\":11,\"Y\":13},{\"X\":5,\"Y\":14},{\"X\":3,\"Y\":12},{\"X\":1,\"Y\":10},{\"X\":13,\"Y\":9}],\"Snakes\":[{\"ID\":\"606fb1fa-ef48-4b9f-8ea8-0859e95809e2\",\"Name\":\"random\",\"URL\":\"\",\"Body\":[{\"X\":5,\"Y\":0},{\"X\":5,\"Y\":1},{\"X\":4,\"Y\":1},{\"X\":4,\"Y\":2},{\"X\":3,\"Y\":2},{\"X\":3,\"Y\":1}],\"Health\":48,\"Death\":null,\"Color\":\"#404040\",\"HeadType\":\"smile\",\"TailType\":\"pixel\"},{\"ID\":\"930cb594-751f-457d-8bae-555d7873c5b8\",\"Name\":\"hungry-food\",\"URL\":\"\",\"Body\":[{\"X\":5,\"Y\":1},{\"X\":5,\"Y\":0},{\"X\":4,\"Y\":0},{\"X\":3,\"Y\":0}],\"Health\":38,\"Death\":{\"Cause\":\"head-collision\",\"Turn\":74},\"Color\":\"#ffff00\",\"HeadType\":\"smile\",\"TailType\":\"curled\"},{\"ID\":\"d1df76a9-f512-4450-9499-e4815f7b5673\",\"Name\":\"eager-food\",\"URL\":\"\",\"Body\":[{\"X\":13,\"Y\":6},{\"X\":13,\"Y\":5},{\"X\":13,\"Y\":4},{\"X\":13,\"Y\":3},{\"X\":13,\"Y\":2},{\"X\":13,\"Y\":1},{\"X\":13,\"Y\":0},{\"X\":12,\"Y\":0},{\"X\":11,\"Y\":0},{\"X\":10,\"Y\":0},{\"X\":9,\"Y\":0},{\"X\":8,\"Y\":0},{\"X\":7,\"Y\":0},{\"X\":6,\"Y\":0},{\"X\":6,\"Y\":1}],\"Health\":93,\"Death\":null,\"Color\":\"#c0c0c0\",\"HeadType\":\"sand-worm\",\"TailType\":\"sharp\"}]}}");

        assertThat(gameStatus.getLastFrame().getSnakes().get(1).getDeath().getCause()).isEqualTo("head-collision");
    }

    @Test
    public void testWriteToJson() throws Exception {
        GameId gameId = new GameId();
        gameId.setId("test-id");
        String json = JsonUtils.toJson(gameId);
        JSONAssert.assertEquals("{\"ID\":\"test-id\"}", json, false);
    }
}

