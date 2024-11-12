package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.AfterEach;

import ca.mcgill.ecse321.gamestore.dao.GameStoreObjectRepository;
import ca.mcgill.ecse321.gamestore.model.*;

@SpringBootTest
class GameStoreObjectRepositoryTest {

	@Autowired
	private GameStoreObjectRepository gameStoreObjectRepository;

	@AfterEach
	public void clearDatabase() {
		gameStoreObjectRepository.deleteAll();
	}

	@Test
	public void testPersistGameStoreObject() {

		// Create object
		GameStoreObject gameStore = new GameStoreObject();

		String policy = "stuff that the customer agrees to";

		gameStore.setPolicy(policy);

		// Save Object
		gameStore = gameStoreObjectRepository.save(gameStore);

		// tests
		assertNotNull(gameStore);
		assertEquals(policy, gameStore.getPolicy());

		// Read object from database using ID
		gameStore = gameStoreObjectRepository.findGameStoreObjectById(gameStore.getId());

		// tests
		assertNotNull(gameStore);
		assertEquals(policy, gameStore.getPolicy());
	}

	@Test
	void testDeleteGameStoreObject() {

		// Create object
		GameStoreObject gameStore = new GameStoreObject();

		String policy = "stuff that the customer agrees to";

		gameStore.setPolicy(policy);

		// Save Object
		gameStore = gameStoreObjectRepository.save(gameStore);

		// tests
		assertNotNull(gameStore);
		assertEquals(policy, gameStore.getPolicy());

		// Delete object from database
		gameStoreObjectRepository.deleteById(gameStore.getId());

		// Assert that database doesn't have object
		assertNull(gameStoreObjectRepository.findGameStoreObjectById(gameStore.getId()));

	}

	@Test
	void testModifyGameStoreObjectAttributes() {

		// Create object
		GameStoreObject gameStore = new GameStoreObject();

		String policy = "stuff that the customer agrees to";

		gameStore.setPolicy(policy);

		// Save Object
		gameStore = gameStoreObjectRepository.save(gameStore);

		// modifiy attribute
		String newPolicy = "new stuff";

		gameStore.setPolicy(newPolicy);
		gameStoreObjectRepository.save(gameStore);

		// read object from database using username
		gameStore = gameStoreObjectRepository.findGameStoreObjectById(gameStore.getId());

		// tests
		assertNotNull(gameStore);
		assertEquals(newPolicy, gameStore.getPolicy());

	}
}
