package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import ca.mcgill.ecse321.gamestore.dao.GameStoreObjectRepository;
import ca.mcgill.ecse321.gamestore.model.GameStoreObject;
import ca.mcgill.ecse321.gamestore.service.GameStoreObjectService;

@SpringBootTest
public class GameStoreObjectServiceTests {

    @Mock
    private GameStoreObjectRepository repo;

    @InjectMocks
    private GameStoreObjectService service;

    @Test
    public void testCreateValidGameStoreObject() {
        // Arrange
        GameStoreObject gameStoreObject = new GameStoreObject("Return Policy");
        when(repo.save(any(GameStoreObject.class))).thenReturn(gameStoreObject);

        // Act
        GameStoreObject createdObject = service.createGameStoreObject("Return Policy");

        // Assert
        assertNotNull(createdObject);
        assertEquals("Return Policy", createdObject.getPolicy());
        verify(repo, times(1)).save(any(GameStoreObject.class));
    }

    @Test
    public void testReadGameStoreObjectByValidId() {
        // Arrange
        GameStoreObject gameStoreObject = new GameStoreObject("Return Policy");
        gameStoreObject.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(gameStoreObject));

        // Act
        GameStoreObject foundObject = service.getGameStoreObjectById(1);

        // Assert
        assertNotNull(foundObject);
        assertEquals(1, foundObject.getId());
        assertEquals("Return Policy", foundObject.getPolicy());
        verify(repo, times(1)).findById(1);
    }

    @Test
    public void testReadGameStoreObjectByInvalidId() {
        // Arrange
        when(repo.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.getGameStoreObjectById(999);
        });
        verify(repo, times(1)).findById(999);
    }

    @Test
    public void testDeleteGameStoreObjectById() {
        // Arrange
        GameStoreObject gameStoreObject = new GameStoreObject("Return Policy");
        gameStoreObject.setId(1);
        when(repo.existsById(1)).thenReturn(true);

        // Act
        service.deleteGameStoreObjectById(1);

        // Assert
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteGameStoreObjectByInvalidId() {
        // Arrange
        when(repo.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteGameStoreObjectById(999);
        });
        verify(repo, times(0)).deleteById(999);
    }
}
