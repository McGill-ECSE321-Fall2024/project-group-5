package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import ca.mcgill.ecse321.gamestore.model.Game;

import java.util.List;

@SpringBootTest
@Transactional
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @AfterEach
    public void clearDatabase() {
        gameRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindById() {
        Game game = new Game();
        game.setName("Test Game");
        game.setPrice(60);
        game.setDescription("Test Description");
        game.setCategory(Game.Category.Action);
        game.setGameConsole(Game.GameConsole.PS5);
        gameRepository.save(game);

        Game foundGame = gameRepository.findById(game.getId())
                .orElse(null);

        assertNotNull(foundGame);
        assertEquals("Test Game", foundGame.getName());
        assertEquals(60, foundGame.getPrice());
    }

    @Test
    public void testFindByCategory() {
        Game game = new Game();
        game.setName("Test Game");
        game.setPrice(60);
        game.setDescription("Test Description");
        game.setCategory(Game.Category.Action);
        game.setGameConsole(Game.GameConsole.PS5);
        gameRepository.save(game);

        List<Game> foundGames = gameRepository.findByCategory(Game.Category.Action);

        assertNotNull(foundGames);
        assertFalse(foundGames.isEmpty());
        assertEquals(Game.Category.Action, foundGames.get(0).getCategory());
    }

    @Test
    public void testFindByGameConsole() {
        Game game = new Game();
        game.setName("Test Game");
        game.setPrice(60);
        game.setDescription("Test Description");
        game.setCategory(Game.Category.Action);
        game.setGameConsole(Game.GameConsole.NintendoSwitch);
        gameRepository.save(game);

        List<Game> foundGames = gameRepository.findByGameConsole(Game.GameConsole.NintendoSwitch);

        assertNotNull(foundGames);
        assertFalse(foundGames.isEmpty());
        assertEquals(Game.GameConsole.NintendoSwitch, foundGames.get(0).getGameConsole());
    }

    @Test
    public void testFindById() {
        Game game = new Game();
        game.setName("Unique Game");
        game.setPrice(60);
        game.setDescription("Test Description");
        game.setCategory(Game.Category.Action);
        game.setGameConsole(Game.GameConsole.PS5);
        gameRepository.save(game);

        Game foundGame = gameRepository.findById(game.getId())
                .orElse(null);

        assertNotNull(foundGame);
        assertEquals("Unique Game", foundGame.getName());
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setName("Test Game");
        game.setPrice(60);
        game.setDescription("Test Description");
        game.setCategory(Game.Category.Action);
        game.setGameConsole(Game.GameConsole.PS5);
        gameRepository.save(game);

        Game foundGame = gameRepository.findById(game.getId())
                .orElse(null);
        assertNotNull(foundGame);
        foundGame.setDescription("Updated Description");
        foundGame.setPrice(75);
        gameRepository.save(foundGame);

        Game updatedGame = gameRepository.findById(foundGame.getId())
                .orElse(null);
        assertNotNull(updatedGame);
        assertEquals("Updated Description", updatedGame.getDescription());
        assertEquals(75, updatedGame.getPrice());
    }

    @Test
    public void testDeleteGame() {
        Game game = new Game();
        game.setName("Test Game");
        game.setPrice(60);
        game.setDescription("Test Description");
        game.setCategory(Game.Category.Action);
        game.setGameConsole(Game.GameConsole.PS5);
        gameRepository.save(game);

        gameRepository.delete(game);

        Game deletedGame = gameRepository.findById(game.getId())
                .orElse(null);
        assertNull(deletedGame);
    }
}
