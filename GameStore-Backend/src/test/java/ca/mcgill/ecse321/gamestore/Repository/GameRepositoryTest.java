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
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
		gameRepository.save(game);

		// Save the game to the repository
		gameRepository.save(game);

		// Retrieve the game by its ID
		Game foundGame = gameRepository.findById(game.getId());

		// Ensure the game was saved and retrieved successfully
		assertNotNull(foundGame);
		assertEquals("Test Game", foundGame.getName());
		assertEquals(60, foundGame.getPrice());
	}

	@Test
	public void testFindByCategory() {
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
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
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.NintendoSwitch);
		gameRepository.save(game);

		// Find games by console
		List<Game> foundGames = gameRepository.findByGameConsole(Game.GameConsole.NintendoSwitch);

		// Ensure the game was found and is associated with the correct console
		assertNotNull(foundGames);
		assertFalse(foundGames.isEmpty());
		assertEquals(Game.GameConsole.NintendoSwitch, foundGames.get(0).getGameConsole());
	}

	@Test
	public void testFindById() {
		// Create a Game and save it
		Game game = new Game();
		game.setName("Unique Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
		gameRepository.save(game);

		// Find games by name
		Game foundGame = gameRepository.findById(game.getId());

		// Ensure the game was found by its name
		assertNotNull(foundGame);
		assertEquals("Unique Game", foundGame.getName());
	}

	@Test
	public void testUpdateGame() {
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
		gameRepository.save(game);

		// Retrieve the game, update its description and price, and save it again
		Game foundGame = gameRepository.findById(game.getId());
		assertNotNull(foundGame);
		foundGame.setDescription("Updated Description");
		foundGame.setPrice(75);
		gameRepository.save(foundGame);

		// Retrieve the updated game and verify the changes
		Game updatedGame = gameRepository.findById(foundGame.getId());
		assertNotNull(updatedGame);
		assertEquals("Updated Description", updatedGame.getDescription());
		assertEquals(75, updatedGame.getPrice());
	}

	@Test
	public void testDeleteGame() {
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
		gameRepository.save(game);

		// Delete the game
		gameRepository.delete(game);

		// Ensure the game is no longer in the repository
		Game deletedGame = gameRepository.findById(game.getId());
		assertNull(deletedGame);
	}
}
