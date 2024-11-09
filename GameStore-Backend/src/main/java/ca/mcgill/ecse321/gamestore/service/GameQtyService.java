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

    @Transactional
    public GameQty getGameQtyById(int id) {
        GameQty gameQty = gameQtyRepository.findById(id);
        if (gameQty == null) {
            throw new IllegalArgumentException("No Qty with this Id exists.");
        }
        return gameQty;
    }

    @Transactional
    public List<GameQty> getGameQtiesByTransactionId(int transactionId) {
        List<GameQty> gameQties = new ArrayList<>();
        gameQtyRepository.findByTransaction_TransactionId(transactionId).forEach(gameQties::add);
        if (gameQties == null || gameQties.isEmpty()) {
            throw new IllegalArgumentException("No GameQties associated with this Transaction.");
        }
        return gameQties;
    }

    @Transactional
    public GameQty deleteGameQty(int id) {
        GameQty gameQty = gameQtyRepository.findById(id);
        if (gameQty == null) {
            throw new IllegalArgumentException("GameQty object does not exist for this Id.");
        }

        gameQtyRepository.delete(gameQty);
        return gameQty;
    }

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