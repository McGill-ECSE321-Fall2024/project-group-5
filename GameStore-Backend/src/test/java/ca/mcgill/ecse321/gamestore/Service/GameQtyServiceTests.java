package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.GameQty;
import ca.mcgill.ecse321.gamestore.model.Transaction;
import ca.mcgill.ecse321.gamestore.service.GameQtyService;
import ca.mcgill.ecse321.gamestore.dao.GameQtyRepository;

@SpringBootTest
public class GameQtyServiceTests {
    @Mock
    private GameQtyRepository repo;

    @InjectMocks
    private GameQtyService service;

    @Test
    public void testCreateValidGameQty() {
        // Arrange
        Game game = new Game();
        game.setName("Super Game");

        Transaction transaction = new Transaction();

        GameQty gameQty = new GameQty();
        gameQty.setGame(game);
        gameQty.setTransaction(transaction);
        gameQty.setQty(5);

        when(repo.save(any(GameQty.class))).thenReturn(gameQty);

        // Act
        GameQty createdGameQty = service.createGameQty(5, transaction, game);

        // Assert
        assertNotNull(createdGameQty);
        assertEquals(game.getId(), createdGameQty.getGame().getId());
        assertEquals(transaction.getTransactionId(), createdGameQty.getTransaction().getTransactionId());
        assertEquals(5, createdGameQty.getQty());
        verify(repo, times(1)).save(any(GameQty.class));
    }

    @Test
    public void testReadGameQtyByValidId() {
        // Arrange
        int id = 1;
        GameQty gameQty = new GameQty();
        gameQty.setQty(10);

        when(repo.findById(id)).thenReturn(gameQty);

        // Act
        GameQty foundGameQty = service.getGameQtyById(id);

        // Assert
        assertNotNull(foundGameQty);
        assertEquals(10, foundGameQty.getQty());
    }

    @Test
    public void testReadGameQtyByInvalidId() {
        // Arrange
        int id = 999;
        when(repo.findById(id)).thenReturn(null);

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> service.getGameQtyById(id));
        assertEquals("Game quantity not found for this id.", e.getMessage());
    }

    @Test
    public void testReadGameQtiesByValidTransactionId() {
        // Arrange
        Transaction transaction = new Transaction();

        GameQty gameQty1 = new GameQty();
        gameQty1.setTransaction(transaction);

        GameQty gameQty2 = new GameQty();
        gameQty2.setTransaction(transaction);

        List<GameQty> gameQties = new ArrayList<>();
        gameQties.add(gameQty1);
        gameQties.add(gameQty2);

        when(repo.findByTransaction_TransactionId(transaction.getTransactionId())).thenReturn(gameQties);

        // Act
        List<GameQty> foundGameQties = service.getGameQtiesByTransactionId(transaction.getTransactionId());

        // Assert
        assertNotNull(foundGameQties);
        assertEquals(2, foundGameQties.size());
        assertEquals(transaction, foundGameQties.get(0).getTransaction());
        assertEquals(transaction, foundGameQties.get(1).getTransaction());
    }

    @Test
    public void testReadGameQtiesByInvalidTransactionId() {
        // Arrange
        int transactionId = 999;
        when(repo.findByTransaction_TransactionId(transactionId)).thenReturn(new ArrayList<>());

        // Act
        List<GameQty> foundGameQties = service.getGameQtiesByTransactionId(transactionId);

        // Assert
        assertNotNull(foundGameQties);
        assertEquals(0, foundGameQties.size());
    }

    @Test
    public void testDeleteGameQtyByValidId() {
        // Arrange
        int id = 1;
        GameQty gameQty = new GameQty();
        gameQty.setQty(5);

        when(repo.findById(id)).thenReturn(gameQty);

        // Act
        GameQty deletedGameQty = service.deleteGameQty(id);

        // Assert
        assertNotNull(deletedGameQty);
        assertEquals(5, deletedGameQty.getQty());
        verify(repo, times(1)).delete(gameQty);
    }

    @Test
    public void testDeleteGameQtyByInvalidId() {
        // Arrange
        int id = 999;
        when(repo.findById(id)).thenReturn(null);

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> service.deleteGameQty(id));
        assertEquals("Game quantity not found for this id.", e.getMessage());
    }

    @Test
    public void testUpdateGameQty() {
        // Arrange
        int id = 1;
        GameQty gameQty = new GameQty();
        gameQty.setQty(5);

        when(repo.findById(id)).thenReturn(gameQty);
        when(repo.save(any(GameQty.class))).thenReturn(gameQty);

        // Act
        GameQty updatedGameQty = service.updateGameQty(id, 10);

        // Assert
        assertNotNull(updatedGameQty);
        assertEquals(10, updatedGameQty.getQty());
        verify(repo, times(1)).save(gameQty);
    }
}
