package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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
        when(repo.save(any(GameStoreObject.class))).thenAnswer(invocation -> {
            GameStoreObject obj = invocation.getArgument(0);
            obj.setPolicy("Return Policy");
            return obj;
        });

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
        int id = 1;
        when(repo.findById(id)).thenReturn(Optional.of(gameStoreObject));

        // Act
        GameStoreObject foundObject = service.getGameStoreObjectById(id);

        // Assert
        assertNotNull(foundObject);
        assertEquals("Return Policy", foundObject.getPolicy());
        verify(repo, times(1)).findById(id);
    }

    @Test
    public void testReadGameStoreObjectByInvalidId() {
        // Arrange
        when(repo.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getGameStoreObjectById(999);
        });
        assertEquals("GameStoreObject not found", exception.getMessage());
        verify(repo, times(1)).findById(999);
    }

    @Test
    public void testDeleteGameStoreObjectById() {
        // Arrange
        int id = 1;
        when(repo.existsById(id)).thenReturn(true);

        // Act
        service.deleteGameStoreObject(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteGameStoreObjectByInvalidId() {
        // Arrange
        int id = 999;
        when(repo.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.deleteGameStoreObject(id);
        });
        assertEquals("GameStoreObject not found", exception.getMessage());
        verify(repo, never()).deleteById(id);
    }
}
