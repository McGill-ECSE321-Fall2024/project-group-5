package ca.mcgill.ecse321.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.transaction.annotation.Transactional;

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
		// Create a new Game
		Game game = new Game("Test Game", 60, "Test Description", Game.Category.Action, Game.GameConsole.PS5, null);

		// Save the game to the repository
		gameRepository.save(game);

		// Retrieve the game by its ID
		Game foundGame = gameRepository.findById(game.getId()).orElse(null);

		// Ensure the game was saved and retrieved successfully
		assertNotNull(foundGame);
		assertEquals("Test Game", foundGame.getName());
		assertEquals(60, foundGame.getPrice());
	}

	@Test
	public void testFindByCategory() {
		// Create and save a Game
		Game game = new Game("Action Game", 50, "Action Game Description", Game.Category.Action, Game.GameConsole.PS4, null);
		gameRepository.save(game);

		// Find games by category
		List<Game> foundGames = gameRepository.findByCategory(Game.Category.Action);

		// Ensure the game was found and has the correct category
		assertNotNull(foundGames);
		assertFalse(foundGames.isEmpty());
		assertEquals(Game.Category.Action, foundGames.get(0).getCategory());
	}

	@Test
	public void testFindByGameConsole() {
		// Create and save a Game
		Game game = new Game("Console Game", 70, "Console Game Description", Game.GameConsole.NintendoSwitch, Game.Category.Puzzle, null);
		gameRepository.save(game);

		// Find games by console
		List<Game> foundGames = gameRepository.findByGameConsole(Game.GameConsole.NintendoSwitch);

		// Ensure the game was found and is associated with the correct console
		assertNotNull(foundGames);
		assertFalse(foundGames.isEmpty());
		assertEquals(Game.GameConsole.NintendoSwitch, foundGames.get(0).getGameConsole());
	}

	@Test
	public void testFindByName() {
		// Create and save a Game
		Game game = new Game("Unique Game", 80, "Unique Description", Game.Category.RPG, Game.GameConsole.XBoxSeriesX, null);
		gameRepository.save(game);

		// Find games by name
		List<Game> foundGames = gameRepository.findByName("Unique Game");

		// Ensure the game was found by its name
		assertNotNull(foundGames);
		assertFalse(foundGames.isEmpty());
		assertEquals("Unique Game", foundGames.get(0).getName());
	}

	@Test
	public void testUpdateGame() {
		// Create and save a Game
		Game game = new Game("Game to Update", 65, "Update Test Description", Game.Category.Sports, Game.GameConsole.WiiU, null);
		gameRepository.save(game);

		// Retrieve the game, update its description and price, and save it again
		Game foundGame = gameRepository.findById(game.getId()).orElse(null);
		assertNotNull(foundGame);
		foundGame.setDescription("Updated Description");
		foundGame.setPrice(75);
		gameRepository.save(foundGame);

		// Retrieve the updated game and verify the changes
		Game updatedGame = gameRepository.findById(foundGame.getId()).orElse(null);
		assertNotNull(updatedGame);
		assertEquals("Updated Description", updatedGame.getDescription());
		assertEquals(75, updatedGame.getPrice());
	}

	@Test
	public void testDeleteGame() {
		// Create and save a Game
		Game game = new Game("Game to Delete", 90, "Delete Test Description", Game.Category.Strategy, Game.GameConsole.PC, null);
		gameRepository.save(game);

		// Delete the game
		gameRepository.delete(game);

		// Ensure the game is no longer in the repository
		Game deletedGame = gameRepository.findById(game.getId()).orElse(null);
		assertNull(deletedGame);
	}
}
