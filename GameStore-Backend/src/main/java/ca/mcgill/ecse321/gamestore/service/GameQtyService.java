package ca.mcgill.ecse321.gamestore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.GameQty;
import ca.mcgill.ecse321.gamestore.model.Transaction;
import ca.mcgill.ecse321.gamestore.dao.GameQtyRepository;
import jakarta.transaction.Transactional;

@Service
public class GameQtyService {
    @Autowired
    private GameQtyRepository gameQtyRepository;

    /**
     * Creates a new GameQty (game quantity) for a transaction and a game.
     * 
     * @param qty         - the quantity of the game
     * @param transaction - the transaction to associate with the game quantity
     * @param game        - the game to associate with the quantity
     * @return the created GameQty
     * @throws IllegalArgumentException if quantity is zero, transaction is null, or
     *                                  game is null
     */
    @Transactional
    public GameQty createGameQty(int qty, Transaction transaction, Game game) {
        if (qty == 0) {
            throw new IllegalArgumentException("Quantity cannot be zero.");
        }
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction is null.");
        }
        if (game == null) {
            throw new IllegalArgumentException("Game is null.");
        }

        GameQty gameQty = new GameQty();
        gameQty.setQty(qty);
        gameQty.setTransaction(transaction);
        gameQty.setGame(game);

        gameQtyRepository.save(gameQty);
        return gameQty;
    }

    /**
     * Retrieves a GameQty by its ID.
     * 
     * @param id - the ID of the game quantity
     * @return the found GameQty
     * @throws IllegalArgumentException if no GameQty is found for the given ID
     */
    @Transactional
    public GameQty getGameQtyById(int id) {
        GameQty gameQty = gameQtyRepository.findById(id);
        if (gameQty == null) {
            throw new IllegalArgumentException("Game quantity not found for this id.");
        }
        return gameQty;
    }

    /**
     * Retrieves a list of GameQtys associated with a specific transaction ID.
     * 
     * @param transactionId - the ID of the transaction
     * @return a list of GameQty objects associated with the transaction ID
     */
    @Transactional
    public List<GameQty> getGameQtiesByTransactionId(int transactionId) {
        List<GameQty> gameQties = new ArrayList<>();
        gameQtyRepository.findByTransaction_TransactionId(transactionId).forEach(gameQties::add);
        return gameQties;
    }

    /**
     * Deletes a GameQty by its ID.
     * 
     * @param id - the ID of the GameQty to delete
     * @return the deleted GameQty
     * @throws IllegalArgumentException if no GameQty is found for the given ID
     */
    @Transactional
    public GameQty deleteGameQty(int id) {
        GameQty gameQty = gameQtyRepository.findById(id);
        if (gameQty == null) {
            throw new IllegalArgumentException("Game quantity not found for this id.");
        }

        gameQtyRepository.delete(gameQty);
        return gameQty;
    }

    /**
     * Updates the quantity of an existing GameQty.
     * 
     * @param id  - the ID of the GameQty to update
     * @param qty - the new quantity to set for the GameQty
     * @return the updated GameQty
     * @throws IllegalArgumentException if no GameQty is found for the given ID
     */
    @Transactional
    public GameQty updateGameQty(int id, int qty) {
        GameQty gameQty = gameQtyRepository.findById(id);

        if (gameQty == null) {
            throw new IllegalArgumentException("No GameQties with this id");
        }
        if (qty == 0) {
            deleteGameQty(id);
            return gameQty;
        }

        gameQty.setQty(qty);
        return gameQtyRepository.save(gameQty);
    }
}