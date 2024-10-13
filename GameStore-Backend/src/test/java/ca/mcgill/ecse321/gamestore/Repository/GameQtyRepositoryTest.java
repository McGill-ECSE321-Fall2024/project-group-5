package ca.mcgill.ecse321.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.GameQty;
import ca.mcgill.ecse321.gamestore.model.Cart;

import java.util.List;

@SpringBootTest
@Transactional
public class GameQtyRepositoryTest {

	@Autowired
	private GameQtyRepository gameQtyRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private CartRepository cartRepository;

	@AfterEach
	public void clearDatabase() {
		gameQtyRepository.deleteAll();
		gameRepository.deleteAll();
		cartRepository.deleteAll();
	}

	@Test
	public void testSaveAndFindById() {
		// Create a Game and save it
		Game game = new Game("Test Game", 60, "Test Description", Game.Category.Action, Game.GameConsole.PS5, null);
		gameRepository.save(game);

		// Create a Cart and save it
		Cart cart = new Cart(1234); // assuming Cart has an id constructor
		cartRepository.save(cart);

		// Create a GameQty and save it
		GameQty gameQty = new GameQty(5, 0, cart, game); // qty = 5
		gameQtyRepository.save(gameQty);

		// Retrieve the GameQty by its ID
		GameQty foundGameQty = gameQtyRepository.findById(gameQty.getId()).orElse(null);

		// Ensure the GameQty was saved and retrieved successfully
		assertNotNull(foundGameQty);
		assertEquals(5, foundGameQty.getQty());
		assertEquals(game.getId(), foundGameQty.getGame().getId());
		assertEquals(cart.getId(), foundGameQty.getCart().getId());
	}

	@Test
	public void testFindByCart() {
		// Create a Game and save it
		Game game = new Game("Cart Game", 45, "Cart Game Description", Game.Category.Sports, Game.GameConsole.PC, null);
		gameRepository.save(game);

		// Create a Cart and save it
		Cart cart = new Cart(4567);
		cartRepository.save(cart);

		// Create a GameQty and associate it with the Cart
		GameQty gameQty = new GameQty(3, 0, cart, game); // qty = 3
		gameQtyRepository.save(gameQty);

		// Find GameQtys by Cart
		List<GameQty> foundGameQtys = gameQtyRepository.findByCart(cart);

		// Ensure the GameQty was found and associated with the correct cart
		assertNotNull(foundGameQtys);
		assertFalse(foundGameQtys.isEmpty());
		assertEquals(cart.getId(), foundGameQtys.get(0).getCart().getId());
	}

	@Test
	public void testFindByGame() {
		// Create a Game and save it
		Game game = new Game("Find Game", 70, "Game Description", Game.Category.RPG, Game.GameConsole.XBoxSeriesX, null);
		gameRepository.save(game);

		// Create a Cart and save it
		Cart cart = new Cart(7890);
		cartRepository.save(cart);

		// Create a GameQty and associate it with the Game
		GameQty gameQty = new GameQty(2, 0, cart, game); // qty = 2
		gameQtyRepository.save(gameQty);

		// Find GameQtys by Game
		List<GameQty> foundGameQtys = gameQtyRepository.findByGame(game);

		// Ensure the GameQty was found and associated with the correct game
		assertNotNull(foundGameQtys);
		assertFalse(foundGameQtys.isEmpty());
		assertEquals(game.getId(), foundGameQtys.get(0).getGame().getId());
	}

	@Test
	public void testUpdateGameQty() {
		// Create a Game and save it
		Game game = new Game("Update Game", 50, "Update Test Description", Game.Category.Strategy, Game.GameConsole.WiiU, null);
		gameRepository.save(game);

		// Create a Cart and save it
		Cart cart = new Cart(1122);
		cartRepository.save(cart);

		// Create a GameQty and save it
		GameQty gameQty = new GameQty(4, 0, cart, game); // qty = 4
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
		Game game = new Game("Delete Game", 80, "Delete Test Description", Game.Category.Multiplayer, Game.GameConsole.Mac, null);
		gameRepository.save(game);

		// Create a Cart and save it
		Cart cart = new Cart(3344);
		cartRepository.save(cart);

		// Create a GameQty and save it
		GameQty gameQty = new GameQty(7, 0, cart, game); // qty = 7
		gameQtyRepository.save(gameQty);

		// Delete the GameQty
		gameQtyRepository.delete(gameQty);

		// Ensure the GameQty was deleted
		GameQty deletedGameQty = gameQtyRepository.findById(gameQty.getId()).orElse(null);
		assertNull(deletedGameQty);
	}
}
