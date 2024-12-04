package ca.mcgill.ecse321.gamestore.Integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import ca.mcgill.ecse321.gamestore.dto.GameRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameResponseDto;
import ca.mcgill.ecse321.gamestore.model.Game;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameIntegrationTests {

    @Autowired
    private TestRestTemplate game;

    @Autowired
    private GameRepository gameRepository;

    private final String VALID_NAME = "The Legend of Zelda";
    private final int VALID_PRICE = 60;
    private final String VALID_DESCRIPTION = "An epic action-adventure game set in a fantasy world.";
    private final Game.Category VALID_CATEGORY = Game.Category.ActionAdventure;
    private final Game.GameConsole VALID_GAME_CONSOLE = Game.GameConsole.PC;
    private final boolean VALID_IN_CATALOG = true;

    private int validId;

    @Test
    public void clearDatabase() {
        gameRepository.deleteAll();
        assertEquals(0, gameRepository.count(), "Database should be empty after clearing");
    }

    @Test
    @Order(1)
    public void testCreateValidGame() {
        // Arrange
        GameRequestDto request = new GameRequestDto(
            VALID_NAME, 
            VALID_PRICE, 
            VALID_DESCRIPTION,
            GameRequestDto.CategoryReqDto.ActionAdventure, 
            GameRequestDto.GameConsoleReqDto.PC, 
            VALID_IN_CATALOG
        );

        // Act
        ResponseEntity<GameResponseDto> response = game.postForEntity("/api/games/new-game", request, GameResponseDto.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        this.validId = response.getBody().getId();
        assertTrue(validId > 0, "Game ID should be greater than 0");
    }

    @Test
    @Order(2)
    public void testReadGameByValidId() {
        // Arrange
        String url = "/api/games/get/" + validId;

        // Act
        ResponseEntity<GameResponseDto> response = game.getForEntity(url, GameResponseDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(validId, response.getBody().getId());
        assertEquals(VALID_NAME, response.getBody().getName());
    }

    @Test
    @Order(3)
    public void testUpdateGame() {
        // Arrange
        GameRequestDto updatedRequest = new GameRequestDto(
            "Updated Name", 
            VALID_PRICE, 
            "Updated Description",
            GameRequestDto.CategoryReqDto.ActionAdventure, 
            GameRequestDto.GameConsoleReqDto.PC, 
            false
        );

        // Act
        game.put("/api/games/update/" + validId, updatedRequest);
        ResponseEntity<GameResponseDto> response = game.getForEntity("/api/games/get/" + validId, GameResponseDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Name", response.getBody().getName());
        assertEquals("Updated Description", response.getBody().getDescription());
        assertFalse(response.getBody().isInCatalog());
    }

    @Test
    @Order(4)
    public void testDeleteGame() {
        // Act
        game.delete("/api/games/delete/" + validId);
        ResponseEntity<GameResponseDto> response = game.getForEntity("/api/games/get/" + validId, GameResponseDto.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
