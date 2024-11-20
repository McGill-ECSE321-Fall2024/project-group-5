package ca.mcgill.ecse321.gamestore.Service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.Game.Category;
import ca.mcgill.ecse321.gamestore.model.Game.GameConsole;
import ca.mcgill.ecse321.gamestore.service.GameService;

@SpringBootTest
public class GameServiceTests {

    @Mock
    private GameRepository repo;

    @InjectMocks
    private GameService service;

    @Test
    public void testCreateValidGame() throws Exception {
        // Arrange
        Game game = new Game();
        // game.setId(1);
        game.setName("Test Game");
        game.setPrice(50);
        game.setDescription("Description for Test Game");
        game.setCategory(Category.Action);
        game.setGameConsole(GameConsole.PS5);
        game.setInCatalog(true);

        when(repo.save(any(Game.class))).thenReturn(game);

        // Act
        Game createdGame = service.addGame(
                "Test Game",
                50,
                "Description for Test Game",
                Category.Action,
                GameConsole.PS5,
                true);

        // Assert
        assertNotNull(createdGame);
        assertEquals("Test Game", createdGame.getName());
        assertEquals(50, createdGame.getPrice());
        assertEquals(Category.Action, createdGame.getCategory());
        verify(repo, times(1)).save(any(Game.class));
    }

    @Test
    public void testReadGameByValidId() throws Exception {
        // Arrange
        int id = 1;
        Game game = new Game();
        game.setId(id);
        game.setName("Test Game");

        when(repo.findById(id)).thenReturn(game);

        // Act
        Game foundGame = service.getGameById(id);

        // Assert
        assertNotNull(foundGame);
        assertEquals(id, foundGame.getId());
        assertEquals("Test Game", foundGame.getName());
        verify(repo, times(1)).findById(id);
    }

    @Test
    public void testReadGameByInvalidId() {
        // Arrange
        int id = 999;
        when(repo.findById(id)).thenReturn(null);

        // Act and Assert
        Exception e = assertThrows(Exception.class, () -> service.getGameById(id));
        assertEquals("No game exists with this ID: 999", e.getMessage());
    }

    @Test
    public void testReadGameByValidName() throws Exception {
        // Arrange
        String name = "Test Game";
        Game game = new Game();
        game.setName(name);

        when(repo.findByName(name)).thenReturn(game);

        // Act
        Game foundGame = service.getGameByName(name);

        // Assert
        assertNotNull(foundGame);
        assertEquals(name, foundGame.getName());
        verify(repo, times(1)).findByName(name);
    }

    @Test
    public void testUpdateGame() throws Exception {
        // Arrange
        int id = 1;
        Game game = new Game();
        game.setId(id);
        game.setName("Test Game");
        game.setPrice(50);

        when(repo.findById(id)).thenReturn(game);
        when(repo.save(any(Game.class))).thenReturn(game);

        // Act
        Game updatedGame = service.updateGame(id, "Updated Game", 70, "Updated Description", Category.ActionAdventure,
                GameConsole.XBoxSeriesS, true);

        // Assert
        assertNotNull(updatedGame);
        assertEquals("Updated Game", updatedGame.getName());
        assertEquals(70, updatedGame.getPrice());
        assertEquals("Updated Description", updatedGame.getDescription());
        verify(repo, times(1)).save(game);
    }

    @Test
    public void testDeleteGameByValidId() {
        // Arrange
        int validId = 1;
        Game game = new Game(); // Assuming you have a Game class, populate it if necessary
        game.setId(validId);

        when(repo.findById(validId)).thenReturn(game); // Simulate finding the game
        when(repo.existsById(validId)).thenReturn(true); // Simulate that the game exists

        // Act
        service.deleteGameById(validId);

        // Assert
        verify(repo, times(1)).delete(game); // Verify that delete was called exactly once
    }

    @Test
    public void testDeleteGameByInvalidId() {
        // Arrange
        int id = -999;
        when(repo.findById(id)).thenReturn(null);

        // Act and Assert
        Exception e = assertThrows(Exception.class, () -> service.deleteGameById(id));
        assertEquals("No game exists with this ID: " + id, e.getMessage());
    }

    @Test
    public void testGetGamesByPartialName() {
        // Arrange
        String partialName = "Test";
        List<Game> games = new ArrayList<>();
        Game game = new Game();
        game.setName("Test Game");
        games.add(game);

        when(repo.findByNameContaining(partialName)).thenReturn(games);

        // Act
        List<Game> foundGames = service.getGamesByPartialName(partialName);

        // Assert
        assertNotNull(foundGames);
        assertEquals(1, foundGames.size());
        assertEquals("Test Game", foundGames.get(0).getName());
        verify(repo, times(1)).findByNameContaining(partialName);
    }

    @Test
    public void testGetGamesByCategory() {
        // Arrange
        Category category = Category.Action;
        List<Game> games = new ArrayList<>();
        Game game = new Game();
        game.setCategory(category);
        games.add(game);

        when(repo.findByCategory(category)).thenReturn(games);

        // Act
        List<Game> foundGames = service.getGamesByCategory(category);

        // Assert
        assertNotNull(foundGames);
        assertEquals(1, foundGames.size());
        assertEquals(Category.Action, foundGames.get(0).getCategory());
        verify(repo, times(1)).findByCategory(category);
    }

    @Test
    public void testListAllGames() {
        // Arrange
        List<Game> games = new ArrayList<>();
        Game game = new Game();
        game.setName("Test Game");
        games.add(game);

        when(repo.findAll()).thenReturn(games);

        // Act
        List<Game> allGames = service.listAllGames();

        // Assert
        assertNotNull(allGames);
        assertEquals(1, allGames.size());
        assertEquals("Test Game", allGames.get(0).getName());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void testInvalidPriceInAddGame() {
        // Arrange & Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.addGame("Test Game", -10, "Description", Category.Action, GameConsole.PS5, true));
        assertEquals("Game price cannot be negative", e.getMessage());
    }
}
