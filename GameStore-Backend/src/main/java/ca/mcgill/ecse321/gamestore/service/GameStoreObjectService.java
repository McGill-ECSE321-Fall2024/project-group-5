package ca.mcgill.ecse321.gamestore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;
import ca.mcgill.ecse321.gamestore.dao.GameStoreObjectRepository;
import jakarta.transaction.Transactional;

@Service
public class GameStoreObjectService {

    @Autowired
    private GameStoreObjectRepository gameStoreObjectRepository;

    @Transactional
    public GameStoreObject createGameStoreObject(String policy) {
        if (policy == null || policy.isEmpty()) {
            throw new IllegalArgumentException("Policy cannot be null or empty.");
        }

        GameStoreObject gameStoreObject = new GameStoreObject();
        gameStoreObject.setPolicy(policy);
        System.out.println("Creating GameStoreObject with policy: " + policy);
        return gameStoreObjectRepository.save(gameStoreObject);
    }

    @Transactional
    public GameStoreObject getGameStoreObjectById(int id) {
        return gameStoreObjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("GameStoreObject not found"));
    }

    @Transactional
    public GameStoreObject updateGameStoreObject(int id, String policy) {
        if (policy == null || policy.isEmpty()) {
            throw new IllegalArgumentException("Policy cannot be null or empty.");
        }

        System.out.println("Updating GameStoreObject with ID: " + id + " to new policy: " + policy);
        GameStoreObject gameStoreObject = getGameStoreObjectById(id); // Throws exception if not found
        gameStoreObject.setPolicy(policy);
        return gameStoreObjectRepository.save(gameStoreObject);
    }

    @Transactional
    public void deleteGameStoreObject(int id) {
        if (!gameStoreObjectRepository.existsById(id)) {
            throw new IllegalArgumentException("GameStoreObject not found");
        }
        gameStoreObjectRepository.deleteById(id);
    }
}
