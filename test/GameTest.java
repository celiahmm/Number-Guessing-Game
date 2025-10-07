import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testGetLevelName_Easy() {

        Game game = new Game();
        String resultat = game.getLevelName(1);
        assertEquals("Easy", resultat);
    }

    @Test
    public void testGetLevelName_Medium() {

        Game game = new Game();
        String resultat = game.getLevelName(2);
        assertEquals("Medium", resultat);
    }

    @Test
    public void testGetLevelName_Hard() {

        Game game = new Game();
        String resultat = game.getLevelName(3);
        assertEquals("Hard", resultat);
    }

    @Test
    public void testGetLevelName_Invalid() {

        Game game = new Game();
        String resultat = game.getLevelName(99);
        assertEquals("Unknown", resultat);
    }
}
