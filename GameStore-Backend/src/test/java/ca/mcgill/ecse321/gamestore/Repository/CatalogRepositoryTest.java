package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import ca.mcgill.ecse321.gamestore.dao.CatalogRepository;
import ca.mcgill.ecse321.gamestore.model.*;

@SpringBootTest
class CatalogRepositoryTest {

	@Autowired
	private CatalogRepository catalogRepository;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		catalogRepository.deleteAll();
	}

	@Test
	public void testPersistCatalog() {

		// Create object
		Catalog catalog = new Catalog();

		/*
		 * Game game1 = new Game();
		 * 
		 * String name = "mario";
		 * int price = 50;
		 * String description = "a game about a plumber";
		 * Game.Category category = Game.Category.Action;
		 * Game.GameConsole gameConsole = Game.GameConsole.WiiU;
		 * 
		 * game1.setName(name);
		 * game1.setPrice(price);
		 * game1.setDescription(description);
		 * game1.setCategory(category);
		 * game1.setGameConsole(gameConsole);
		 * catalog.addGame(game1);
		 */

		// Save Objects
		catalog = catalogRepository.save(catalog);

		// tests
		assertNotNull(catalog);
		// assertEquals(game1, catalog.getGame(0));

		// Read object from database using ID
		catalog = catalogRepository.getCatalogByIdIsNotNull();

		// tests
		assertNotNull(catalog);
		// assertEquals(game1, catalog.getGame(0));
	}

	@Test
	void testDeleteCatalog() {

		// Create object
		Catalog catalog = new Catalog();

		/*
		 * Game game1 = new Game();
		 * 
		 * String name = "mario";
		 * int price = 50;
		 * String description = "a game about a plumber";
		 * Game.Category category = Game.Category.Action;
		 * Game.GameConsole gameConsole = Game.GameConsole.WiiU;
		 * 
		 * game1.setName(name);
		 * game1.setPrice(price);
		 * game1.setDescription(description);
		 * game1.setCategory(category);
		 * game1.setGameConsole(gameConsole);
		 * catalog.addGame(game1);
		 */

		// Save Object
		catalog = catalogRepository.save(catalog);

		assertNotNull(catalog);

		// delete object
		catalogRepository.deleteCatalogByIdNotNull();

		// tests
		assertNull(catalogRepository.getCatalogByIdIsNotNull());
	}
}
