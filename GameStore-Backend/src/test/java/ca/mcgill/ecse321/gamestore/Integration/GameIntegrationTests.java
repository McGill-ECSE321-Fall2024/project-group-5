package ca.mcgill.ecse321.gamestore.Integration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import ca.mcgill.ecse321.gamestore.dto.GameRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameRequestDto.CategoryReqDto;
import ca.mcgill.ecse321.gamestore.dto.GameRequestDto.GameConsoleReqDto;
import ca.mcgill.ecse321.gamestore.dto.GameResponseDto;
import ca.mcgill.ecse321.gamestore.model.Game.Category;
import ca.mcgill.ecse321.gamestore.model.Game.GameConsole;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @TestInstance(Lifecycle.PER_CLASS)
public class GameIntegrationTests {

    @Autowired
    private TestRestTemplate game;

    @Autowired
    private GameRepository gameRepository;

    
    @Test //clears database before running tests
    public void clearDatabase() {
        gameRepository.deleteAll();
    }

    private final String VALID_NAME = "The Legend of Zelda";
    private final int VALID_PRICE = 60;
    private final String VALID_DESCRIPTION = "An epic action-adventure game set in a fantasy world.";
    private final Category VALID_CATEGORY = Category.ActionAdventure;
    private final GameConsole VALID_GAME_CONSOLE = GameConsole.PC;
    private final boolean VALID_IN_CATALOG = true;

    private int validId;


    @Test
    @Order(1)
    public void testCreateValidGame() {
        // Arrange: Initialize the request DTO with valid data
        GameRequestDto request = new GameRequestDto(
                VALID_NAME,
                VALID_PRICE,
                VALID_DESCRIPTION,
                CategoryReqDto.ActionAdventure,
                GameConsoleReqDto.PC,
                VALID_IN_CATALOG);

        // Act: Send POST request to create the game
        ResponseEntity<GameResponseDto> response = game.postForEntity("/api/games/new-game", request, GameResponseDto.class);

        // Assert: Verify response status and data integrity
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        GameResponseDto createdGame = response.getBody();
        assertNotNull(createdGame);
        assertEquals(VALID_NAME, createdGame.getName());
        assertEquals(VALID_CATEGORY.name(), createdGame.getCategory().name(), "Game category should match");
        assertEquals(VALID_GAME_CONSOLE.name(), createdGame.getGameConsole().name(), "Game console should match");
        assertEquals(VALID_PRICE, createdGame.getPrice(), "Game price should match");
        assertEquals(VALID_DESCRIPTION, createdGame.getDescription(), "Game description should match");
        assertEquals(VALID_IN_CATALOG, createdGame.isInCatalog(), "Game catalog status should match");

        assertNotNull(createdGame.getId(), "Game ID should be set after creation");
        assertTrue(createdGame.getId() > 0, "Game ID should be positive");

        // Save the game ID for the next test
        this.validId = createdGame.getId();
    }
    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetGameByValidId() {
        // Arrange: Construct URL with valid ID
        String url = "/api/games/get/" + validId;

        // Act: Send GET request
        ResponseEntity<GameResponseDto> response = game.getForEntity(url, GameResponseDto.class);

        // Assert: Validate response and data
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        GameResponseDto retrievedGame = response.getBody();
        assertNotNull(retrievedGame);
        assertEquals(validId, retrievedGame.getId());
        assertEquals("Test Game", retrievedGame.getName());
        assertEquals(50, retrievedGame.getPrice());
        assertEquals("This is a test game", retrievedGame.getDescription());
        assertEquals(CategoryReqDto.ActionAdventure.name(), retrievedGame.getCategory().name());
        assertEquals(GameConsoleReqDto.PC.name(), retrievedGame.getGameConsole().name());
        assertEquals(true, retrievedGame.isInCatalog());
    }

    @Test
    @Order(3)
    public void testCreateGameWithInvalidPrice() {
        // Arrange: Create a GameRequestDto with invalid price (negative value)
        GameRequestDto request = new GameRequestDto(
                VALID_NAME,
                -10, // Invalid price
                VALID_DESCRIPTION,
                CategoryReqDto.ActionAdventure,
                GameConsoleReqDto.PC,
                VALID_IN_CATALOG);

        // Act: Send POST request to create the game
        ResponseEntity<GameResponseDto> response = game.postForEntity("/api/games/new-game", request, GameResponseDto.class);;

        // Assert: Verify response status code is BAD_REQUEST
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Expected status code 400 (BAD_REQUEST)");
        // assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    public void testGetGameByInvalidId() {
        // Arrange: Use a non-existent game ID
        int invalidId = -1; // Invalid ID

        String url = "/api/games/get/" + invalidId;

        // Act: Send GET request to retrieve the game by invalid ID
        ResponseEntity<String> response = game.getForEntity(url, String.class);

        // Assert: Verify response status code is NOT_FOUND
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Expected status code 404 (NOT_FOUND)");
    }

    @SuppressWarnings("null")
    @Test
    @Order(5)
    public void testUpdateGame() {
        // Ensure a valid game is created first
        // assertTrue(validId > 0, "Valid ID should be set after creating a game");

        // Arrange: Create the updated GameRequestDto
        GameRequestDto updatedRequest = new GameRequestDto(
                "Updated Game Name",
                VALID_PRICE,
                "Updated description of the game.",
                CategoryReqDto.ActionAdventure,
                GameConsoleReqDto.PC,
                false // Update catalog status to false
        );

        String updateUrl = "/api/games/update/" + validId;

        // Act: Send PUT request to update the game
        game.put(updateUrl, updatedRequest);

        // Act: Retrieve the updated game
        ResponseEntity<GameResponseDto> response = game.getForEntity("/api/games/get/" + validId, GameResponseDto.class);

        // Assert: Verify the updated game data
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        GameResponseDto updatedGame = response.getBody();
        assertNotNull(updatedGame, "Game response body should not be null");
        assertEquals("Updated Game Name", updatedGame.getName(), "Game name should be updated");
        assertEquals("Updated description of the game.", updatedGame.getDescription(),
                "Game description should be updated");
        assertEquals(false, updatedGame.isInCatalog(), "Game catalog status should be updated");
    }

    @Test
    @Order(6)
    public void testDeleteGame() {       

        // Arrange: Create the delete URL
        // String deleteUrl = GAME_API_URL + "/delete/" + validId;
        String deleteUrl = "/api/games/delete/" + this.validId;

        // Act: Send DELETE request to delete the game
        game.delete(deleteUrl);

        // Act: Attempt to retrieve the deleted game
        String fetchUrl = "/api/games/get/" + this.validId;
        ResponseEntity<GameResponseDto> response = game.getForEntity(fetchUrl, GameResponseDto.class);

        // Assert: Verify response status is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Expected status code 404 (NOT_FOUND)");
    }

    @SuppressWarnings({ "null", "rawtypes" })
    @Test
    @Order(7)
    public void testGetAllGames() {
        // Arrange: Create the URL for listing games
        String listUrl = "/api/games/get/allgames";

        // Act: Send GET request to list games
        ResponseEntity<List> response = game.exchange(listUrl, HttpMethod.GET, null, List.class);

        // Assert: Verify response status is OK
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code 200 (OK)");

        // Check that the response contains a non-empty list of games
        assertTrue(response.getBody().size() > 0, "The game list should not be empty");
    }

}


