package ca.mcgill.ecse321.gamestore.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        when(repo.findById(id)).thenReturn(Optional.of(game));

        // Act
        Game foundGame = service.getGameById(id);

        // Assert
        assertNotNull(foundGame);
        assertEquals(id, foundGame.getId());
        assertEquals("Test Game", foundGame.getName());
        verify(repo, times(1)).findById(id);
    }

    /*
     * @Test
     * public void testReadGameByInvalidId() {
     * // Arrange
     * int id = 999;
     * when(repo.findById(id)).thenReturn(null);
     * 
     * // Act and Assert
     * Exception e = assertThrows(Exception.class, () -> service.getGameById(id));
     * assertEquals("No game exists with this ID: 999", e.getMessage());
     * }
     */

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

        when(repo.findById(id)).thenReturn(Optional.of(game));
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

    /*
     * @Test
     * public void testDeleteGameByValidId() {
     * // Arrange
     * int validId = 1;
     * Game game = new Game(); // Assuming you have a Game class, populate it if
     * necessary
     * game.setId(validId);
     * 
     * when(repo.findById(validId)).thenReturn(Optional.of(game)); // Simulate
     * finding the game
     * when(repo.existsById(validId)).thenReturn(true); // Simulate that the game
     * exists
     * 
     * // Act
     * service.deleteGameById(validId);
     * 
     * // Assert
     * verify(repo, times(1)).delete(game); // Verify that delete was called exactly
     * once
     * }
     */

    @Test
    public void testDeleteGameByInvalidId() {
        // Arrange
        int id = -999;
        when(repo.findById(id)).thenReturn(null);

        // Act and Assert
        Exception e = assertThrows(Exception.class, () -> service.deleteGameById(id));
        assertEquals("Game with ID -999 not found.", e.getMessage());
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
        assertEquals("Price cannot be negative", e.getMessage());
    }

    @Test
    public void testAddGameWithEmptyName() {
        // Arrange, Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.addGame("", 50, "Description", Category.Action, GameConsole.PS5, true));
        assertEquals("Game name cannot be null or empty", e.getMessage());
    }

    @Test
    public void testAddGameWithNullCategory() {
        // Arrange, Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.addGame("Test Game", 50, "Description", null, GameConsole.PS5, true));
        assertEquals("Category is required", e.getMessage());
    }

    @Test
    public void testAddGameWithNullGameConsole() {
        // Arrange, Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.addGame("Test Game", 50, "Description", Category.Action, null, true));
        assertEquals("Game console is required", e.getMessage());
    }

    /*
     * @Test
     * public void testUpdateGameWithInvalidId() {
     * // Arrange
     * int invalidId = 999;
     * when(repo.findById(invalidId)).thenReturn(null);
     * 
     * // Act & Assert
     * Exception e = assertThrows(IllegalArgumentException.class,
     * () -> service.updateGame(invalidId, "Updated Game", 70,
     * "Updated Description", Category.ActionAdventure,
     * GameConsole.XBoxSeriesS, true));
     * assertEquals("No game exists with this ID: 999", e.getMessage());
     * }
     */

    @Test
    public void testDeleteNonExistentGame() {
        // Arrange
        int nonExistentId = 2;
        when(repo.existsById(nonExistentId)).thenReturn(false);

        // Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.deleteGameById(nonExistentId));
        assertEquals("Game with ID 2 not found.", e.getMessage());
    }

    @Test
    public void testGetGamesByGameConsole() {
        // Arrange
        GameConsole gameConsole = GameConsole.PS5;
        List<Game> games = new ArrayList<>();
        Game game = new Game();
        game.setGameConsole(gameConsole);
        games.add(game);

        when(repo.findByGameConsole(gameConsole)).thenReturn(games);

        // Act
        List<Game> foundGames = service.getGamesByGameConsole(gameConsole);

        // Assert
        assertNotNull(foundGames);
        assertEquals(1, foundGames.size());
        assertEquals(GameConsole.PS5, foundGames.get(0).getGameConsole());
        verify(repo, times(1)).findByGameConsole(gameConsole);
    }

    @Test
    public void testGetGamesByNullPartialName() {
        // Arrange, Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.getGamesByPartialName(null));
        assertEquals("Partial name cannot be null or empty", e.getMessage());
    }

    @Test
    public void testListAllGamesWhenNoneExist() {
        // Arrange
        when(repo.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Game> allGames = service.listAllGames();

        // Assert
        assertNotNull(allGames);
        assertEquals(0, allGames.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void testUpdateGameWithEmptyName() {
        // Arrange
        int id = 1;
        Game game = new Game();
        game.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        // Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.updateGame(id, "", 70, "Updated Description", Category.ActionAdventure,
                        GameConsole.XBoxSeriesS, true));
        assertEquals("Game name cannot be null or empty", e.getMessage());
    }

    @Test
    public void testListAllGamesReturnsEmptyList() {
        // Arrange
        when(repo.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Game> games = service.listAllGames();

        // Assert
        assertNotNull(games);
        assertEquals(0, games.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void testAddDuplicateGame() {
        // Arrange
        String name = "Duplicate Game";
        when(repo.findByName(name)).thenReturn(new Game());

        // Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.addGame(name, 50, "Duplicate Description", Category.Action, GameConsole.PS5, true));
        assertEquals("A game with this name already exists", e.getMessage());
    }

    @Test
    public void testUpdateGameWithNegativePrice() {
        // Arrange
        int id = 1;
        Game game = new Game();
        game.setId(id);
        when(repo.findById(id)).thenReturn(Optional.of(game));

        // Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.updateGame(id, "Test Game", -10, "Updated Description", Category.Action, GameConsole.PS5,
                        true));
        assertEquals("Price cannot be negative", e.getMessage());
    }

    @Test
    public void testAddGameWithNullDescription() {
        // Arrange, Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.addGame("Test Game", 50, null, Category.Action, GameConsole.PS5, true));
        assertEquals("Description cannot be null or empty", e.getMessage());
    }

    @Test
    public void testDeleteGameNotInCatalog() {
        // Arrange
        int id = 1;
        Game game = new Game();
        game.setId(id);
        game.setInCatalog(false);

        when(repo.findById(id)).thenReturn(Optional.of(game));

        // Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.deleteGameById(id));
        assertEquals("Game with ID 1 not found.", e.getMessage());
    }

    @Test
    public void testUpdateGameWithNullDescription() {
        // Arrange
        int id = 1;
        Game game = new Game();
        game.setId(id);
        when(repo.findById(id)).thenReturn(Optional.of(game));

        // Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.updateGame(id, "Updated Game", 50, null, Category.ActionAdventure, GameConsole.PS5,
                        true));
        assertEquals("Description cannot be null or empty", e.getMessage());
    }

    @Test
    public void testAddGameWithNullInCatalog() {
        // Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.addGame("Test Game", 50, "Description", Category.Action, GameConsole.PS5, false));
        assertEquals("New games must be in the catalog", e.getMessage());
    }

    /*
     * @Test
     * public void testGetGameByIdWhenRepositoryEmpty() {
     * // Arrange
     * when(repo.findById(anyInt())).thenReturn(null);
     * 
     * // Act & Assert
     * Exception e = assertThrows(Exception.class, () -> service.getGameById(1));
     * assertEquals("No game exists with this ID: 1", e.getMessage());
     * }
     */

    @Test
    public void testUpdateGameWithNoChanges() throws Exception {
        // Arrange
        int id = 1;
        Game game = new Game();
        game.setId(id);
        game.setName("Original Game");
        game.setPrice(50);
        game.setDescription("Original Description");

        when(repo.findById(id)).thenReturn(Optional.of(game));
        when(repo.save(game)).thenReturn(game);

        // Act
        Game updatedGame = service.updateGame(id, "Original Game", 50, "Original Description",
                Category.Action, GameConsole.PS5, true);

        // Assert
        assertNotNull(updatedGame);
        assertEquals("Original Game", updatedGame.getName());
        verify(repo, times(1)).save(game);
    }

    @Test
    public void testDeleteGameWhenRepositoryIsEmpty() {
        // Arrange
        int id = 1;
        when(repo.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.deleteGameById(id));
        assertEquals("Game with ID 1 not found.", e.getMessage());
    }

}
