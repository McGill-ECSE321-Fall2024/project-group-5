package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.GameQty;
import ca.mcgill.ecse321.gamestore.dao.GameQtyRepository;
import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import ca.mcgill.ecse321.gamestore.dao.TransactionRepository;
import ca.mcgill.ecse321.gamestore.model.Transaction;

import java.util.List;

@SpringBootTest
@Transactional
public class GameQtyRepositoryTest {

	@Autowired
	private GameQtyRepository gameQtyRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@AfterEach
	public void clearDatabase() {
		gameQtyRepository.deleteAll();
		gameRepository.deleteAll();
		transactionRepository.deleteAll();
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

		// Create a Transaction and save it
		Transaction transaction = new Transaction(); // assuming Transaction has an id constructor
		transactionRepository.save(transaction);

		// Create a GameQty and save it
		GameQty gameQty = new GameQty();
		gameQty.setQty(5);
		gameQty.setGame(game);
		gameQty.setTransaction(transaction);

		gameQtyRepository.save(gameQty);

		// Retrieve the GameQty by its ID
		GameQty foundGameQty = gameQtyRepository.findById(gameQty.getId()).orElse(null);

		// Ensure the GameQty was saved and retrieved successfully
		assertNotNull(foundGameQty);
		assertEquals(5, foundGameQty.getQty());
		assertEquals(game.getId(), foundGameQty.getGame().getId());
		assertEquals(transaction.getTransactionId(), foundGameQty.getTransaction().getTransactionId());
	}

	@Test
	public void testFindByTransaction() {
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
		gameRepository.save(game);

		// Create a Transaction and save it
		Transaction transaction = new Transaction(); // assuming Transaction has an id constructor
		transactionRepository.save(transaction);

		// Create a GameQty and save it
		GameQty gameQty = new GameQty();
		gameQty.setQty(5);
		gameQty.setGame(game);
		gameQty.setTransaction(transaction);
		gameQtyRepository.save(gameQty);

		// Find GameQtys by Transaction
		List<GameQty> foundGameQtys = gameQtyRepository.findByTransaction_TransactionId(transaction.getTransactionId());

		// Ensure the GameQty was found and associated with the correct transaction
		assertNotNull(foundGameQtys);
		assertFalse(foundGameQtys.isEmpty());
		assertEquals(transaction.getTransactionId(), foundGameQtys.get(0).getTransaction().getTransactionId());
	}

	@Test
	public void testFindByGame() {
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
		gameRepository.save(game);

		// Create a Transaction and save it
		Transaction transaction = new Transaction(); // assuming Transaction has an id constructor
		transactionRepository.save(transaction);

		// Create a GameQty and save it
		GameQty gameQty = new GameQty();
		gameQty.setQty(5);
		gameQty.setGame(game);
		gameQtyRepository.save(gameQty);

		// Find GameQtys by Game
		List<GameQty> foundGameQtys = gameQtyRepository.findByGame_Id(game.getId());

		// Ensure the GameQty was found and associated with the correct game
		assertNotNull(foundGameQtys);
		assertFalse(foundGameQtys.isEmpty());
		assertEquals(game.getId(), foundGameQtys.get(0).getGame().getId());
	}

	@Test
	public void testUpdateGameQty() {
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
		gameRepository.save(game);

		// Create a Transaction and save it
		Transaction transaction = new Transaction(); // assuming Transaction has an id constructor
		transactionRepository.save(transaction);

		// Create a GameQty and save it
		GameQty gameQty = new GameQty();
		gameQty.setQty(5);
		gameQty.setGame(game);
		gameQtyRepository.save(gameQty);

		// Update the quantity of the GameQty and save it again
		GameQty foundGameQty = gameQtyRepository.findById(gameQty.getId()).orElse(null);
		assertNotNull(foundGameQty);
		foundGameQty.setQty(10);
		gameQtyRepository.save(foundGameQty);

		// Retrieve the updated GameQty and verify the change
		GameQty updatedGameQty = gameQtyRepository.findById(foundGameQty.getId()).orElse(null);
		assertNotNull(updatedGameQty);
		assertEquals(10, updatedGameQty.getQty());
	}

	@Test
	public void testDeleteGameQty() {
		// Create a Game and save it
		Game game = new Game();
		game.setName("Test Game");
		game.setPrice(60);
		game.setDescription("Test Description");
		game.setCategory(Game.Category.Action);
		game.setGameConsole(Game.GameConsole.PS5);
		gameRepository.save(game);

		// Create a Transaction and save it
		Transaction transaction = new Transaction(); // assuming Transaction has an id constructor
		transactionRepository.save(transaction);

		// Create a GameQty and save it
		GameQty gameQty = new GameQty();
		gameQty.setQty(5);
		gameQty.setGame(game);
		gameQtyRepository.save(gameQty);

		// Delete the GameQty
		gameQtyRepository.delete(gameQty);

		// Ensure the GameQty was deleted
		GameQty deletedGameQty = gameQtyRepository.findById(gameQty.getId()).orElse(null);
		assertNull(deletedGameQty);
	}
}
