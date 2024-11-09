package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.AfterEach;

import ca.mcgill.ecse321.gamestore.dao.GameStoreObjectRepository;
import ca.mcgill.ecse321.gamestore.dao.PromotionCodeRepository;
import ca.mcgill.ecse321.gamestore.model.*;

@SpringBootTest
class PromotionCodeRepositoryTest {

	@Autowired
	private PromotionCodeRepository promotionCodeRepository;
	@Autowired
	private GameStoreObjectRepository gameStoreObjectRepository;

	@AfterEach
	public void clearDatabase() {
		promotionCodeRepository.deleteAll();
		gameStoreObjectRepository.deleteAll();
	}

	@Test
	public void testPersistPromotionCode() {

		// Create object

		GameStoreObject gameStore = new GameStoreObject();

		String policy = "stuff that the customer agrees to";

		gameStore.setPolicy(policy);

		PromotionCode promotionCode = new PromotionCode();

		String code = "someCode";
		int value = 30;

		promotionCode.setCode(code);
		promotionCode.setPercentageValue(value);
		promotionCode.setGameStoreObject(gameStore);

		// Save Object
		gameStore = gameStoreObjectRepository.save(gameStore);
		promotionCode = promotionCodeRepository.save(promotionCode);

		// Read object from database using ID
		promotionCode = promotionCodeRepository.getPromotionCodeById(promotionCode.getId());

		// tests
		assertNotNull(promotionCode);
		assertEquals(code, promotionCode.getCode());
		assertEquals(value, promotionCode.getPercentageValue());
	}

	@Test
	void testDeletePromotionCode() {

		// Create object

		GameStoreObject gameStore = new GameStoreObject();

		String policy = "stuff that the customer agrees to";

		gameStore.setPolicy(policy);

		PromotionCode promotionCode = new PromotionCode();

		String code = "someCode";
		int value = 30;

		promotionCode.setCode(code);
		promotionCode.setPercentageValue(value);
		promotionCode.setGameStoreObject(gameStore);

		// Save Object
		gameStore = gameStoreObjectRepository.save(gameStore);
		promotionCode = promotionCodeRepository.save(promotionCode);

		// Delete object from database
		promotionCodeRepository.deletePromotionCodeById(promotionCode.getId());

		// Assert that database doesn't have object
		assertNull(promotionCodeRepository.getPromotionCodeById(promotionCode.getId()));

	}
}
