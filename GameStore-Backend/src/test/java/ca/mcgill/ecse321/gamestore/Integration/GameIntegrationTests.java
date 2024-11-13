package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dto.GameRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameRequestDto.CategoryReqDto;
import ca.mcgill.ecse321.gamestore.dto.GameRequestDto.GameConsoleReqDto;
import ca.mcgill.ecse321.gamestore.dto.GameResponseDto;
import ca.mcgill.ecse321.gamestore.model.Game.Category; // Assuming Category is an enum
import ca.mcgill.ecse321.gamestore.model.Game.GameConsole; // Assuming GameConsole is an enum

// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @TestInstance(Lifecycle.PER_CLASS)
// public class GameIntegrationTests {

//     @Autowired
//     private TestRestTemplate game;

//     private final String VALID_NAME = "The Legend of Zelda";
//     private final int VALID_PRICE = 60;
//     private final String VALID_DESCRIPTION = "An epic action-adventure game set in a fantasy world.";
//     private final Category VALID_CATEGORY = Category.ActionAdventure; // Assuming it's an enum
//     private final GameConsole VALID_GAME_CONSOLE = GameConsole.PC; // Assuming it's an enum
//     private final boolean VALID_IN_CATALOG = true;

//     private int validId;

//     @Test
//     @Order(1)
//     public void testCreateValidGame() {
//         // Arrange: Create a GameRequestDto with valid data, excluding 'gameID' as it's auto-generated
//         GameRequestDto request = new GameRequestDto(
//         VALID_NAME,
//         VALID_PRICE,
//         VALID_DESCRIPTION,
//         CategoryReqDto.ActionAdventure, // use CategoryReqDto instead of Category
//         GameConsoleReqDto.PC,           // use GameConsoleReqDto instead of GameConsole
//         VALID_IN_CATALOG
//         );

//         // Act: Send a POST request to create a new game
//         ResponseEntity<GameResponseDto> response = game.postForEntity("/api/games/newgame", request, GameResponseDto.class);

//         // Assert: Verify the response is not null and has a CREATED status
//         assertNotNull(response);
//         assertEquals(HttpStatus.CREATED, response.getStatusCode());

//         GameResponseDto createdGame = response.getBody();
//         assertNotNull(createdGame);
//         assertEquals(VALID_NAME, createdGame.getName());
//         assertEquals(VALID_CATEGORY.name(), createdGame.getCategory().name());  // Compare using name() to avoid enum mismatch
//         assertEquals(VALID_GAME_CONSOLE.name(), createdGame.getGameConsole().name());
//         assertEquals(VALID_PRICE, createdGame.getPrice());
//         assertEquals(VALID_DESCRIPTION, createdGame.getDescription());
//         assertEquals(VALID_IN_CATALOG, createdGame.isInCatalog());  // Verify 'inCatalog' value
//         assertNotNull(createdGame.getId());
//         assertTrue(createdGame.getId() > 0, "Response should have a positive ID.");

//         // Store the valid ID for use in the next test
//         this.validId = createdGame.getId();
//     }

//     @Test
//     @Order(2)
//     public void testReadGameByValidId() {
//         // Ensure validId is not zero (it should have been set in the previous test)
//         assertTrue(validId > 0, "Valid ID should be set after creating a game.");

//         // Arrange: Create the URL to access the game by its valid ID
//         String url = "/api/games/get/" + this.validId;

//         // Act: Send a GET request to retrieve the game by ID
//         ResponseEntity<GameResponseDto> response = game.getForEntity(url, GameResponseDto.class);

//         // Assert: Verify the response is not null and has an OK status
//         assertNotNull(response);
//         assertEquals(HttpStatus.OK, response.getStatusCode());

//         GameResponseDto game = response.getBody();
//         assertNotNull(game);
//         assertEquals(VALID_NAME, game.getName());
//         assertEquals(VALID_CATEGORY.name(), game.getCategory().name());  // Compare using name() to avoid enum mismatch
//         assertEquals(VALID_GAME_CONSOLE.name(), game.getGameConsole().name());
//         assertEquals(VALID_PRICE, game.getPrice());
//         assertEquals(VALID_DESCRIPTION, game.getDescription());
//         assertEquals(VALID_IN_CATALOG, game.isInCatalog());  // Verify 'inCatalog' value
//         assertEquals(this.validId, game.getId());
//     }

// }


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameIntegrationTests {

    @Autowired
    private TestRestTemplate game;

    private final String VALID_NAME = "The Legend of Zelda";
    private final int VALID_PRICE = 60;
    private final String VALID_DESCRIPTION = "An epic action-adventure game set in a fantasy world.";
    private final Category VALID_CATEGORY = Category.ActionAdventure; // Assuming it's an enum
    private final GameConsole VALID_GAME_CONSOLE = GameConsole.PC; // Assuming it's an enum
    private final boolean VALID_IN_CATALOG = true;

    private int validId;

    private static final String GAME_API_URL = "/api/games";
    private static final String CREATE_GAME_URL = GAME_API_URL + "/newgame";
    private static final String GET_GAME_URL = GAME_API_URL + "/get/";

    @Test
    @Order(1)
    public void testCreateValidGame() {
        // Arrange: Create a GameRequestDto with valid data, excluding 'gameID' as it's auto-generated
        GameRequestDto request = new GameRequestDto(
                VALID_NAME,
                VALID_PRICE,
                VALID_DESCRIPTION,
                CategoryReqDto.ActionAdventure, // use CategoryReqDto instead of Category
                GameConsoleReqDto.PC,           // use GameConsoleReqDto instead of GameConsole
                VALID_IN_CATALOG
        );

        // Act: Send a POST request to create a new game
        ResponseEntity<GameResponseDto> response = game.postForEntity(CREATE_GAME_URL, request, GameResponseDto.class);

        // Assert: Verify the response is not null and has a CREATED status
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Status code should be CREATED");

        GameResponseDto createdGame = response.getBody();
        assertNotNull(createdGame, "Created game should not be null");
        assertEquals(VALID_NAME, createdGame.getName());
        assertEquals(VALID_CATEGORY.name(), createdGame.getCategory().name());  // Compare using name() to avoid enum mismatch
        assertEquals(VALID_GAME_CONSOLE.name(), createdGame.getGameConsole().name());
        assertEquals(VALID_PRICE, createdGame.getPrice());
        assertEquals(VALID_DESCRIPTION, createdGame.getDescription());
        assertEquals(VALID_IN_CATALOG, createdGame.isInCatalog());  // Verify 'inCatalog' value
        assertNotNull(createdGame.getId(), "Game ID should be set after creation");
        assertTrue(createdGame.getId() > 0, "Response should have a positive ID.");

        // Store the valid ID for use in the next test
        this.validId = createdGame.getId();
    }

    @Test
    @Order(2)
    public void testReadGameByValidId() {
        // Ensure validId is not zero (it should have been set in the previous test)
        assertTrue(validId > 0, "Valid ID should be set after creating a game.");

        // Arrange: Create the URL to access the game by its valid ID
        String url = GET_GAME_URL + this.validId;

        // Act: Send a GET request to retrieve the game by ID
        ResponseEntity<GameResponseDto> response = game.getForEntity(url, GameResponseDto.class);

        // Assert: Verify the response is not null and has an OK status
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");

        GameResponseDto game = response.getBody();
        assertNotNull(game, "Game body should not be null");
        assertEquals(VALID_NAME, game.getName());
        assertEquals(VALID_CATEGORY.name(), game.getCategory().name());  // Compare using name() to avoid enum mismatch
        assertEquals(VALID_GAME_CONSOLE.name(), game.getGameConsole().name());
        assertEquals(VALID_PRICE, game.getPrice());
        assertEquals(VALID_DESCRIPTION, game.getDescription());
        assertEquals(VALID_IN_CATALOG, game.isInCatalog());  // Verify 'inCatalog' value
        assertEquals(this.validId, game.getId(), "Game ID should match the valid ID.");
    }
}
