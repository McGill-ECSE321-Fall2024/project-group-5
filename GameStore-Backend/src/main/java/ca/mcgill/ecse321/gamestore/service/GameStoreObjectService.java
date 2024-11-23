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
            throw new IllegalArgumentException("Policy cannot be null or empty");
        }

        // Create a new GameStoreObject and set its attributes
        GameStoreObject gameStoreObject = new GameStoreObject();
        gameStoreObject.setPolicy(policy);

        // Save the new object and return it
        return gameStoreObjectRepository.save(gameStoreObject);
    }

    @Transactional
    public GameStoreObject getGameStoreObjectById(int id) {
        Optional<GameStoreObject> gameStoreObject = gameStoreObjectRepository.findById(id);
        return gameStoreObject.orElseThrow(() -> new IllegalArgumentException("GameStoreObject not found"));
    }


    @Transactional
    public GameStoreObject updateGameStoreObject(int id, String policy) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive integer.");
        }
        if (policy == null || policy.isEmpty()) {
            throw new IllegalArgumentException("Policy cannot be null or empty.");
        }

        // Fetch the object to update
        GameStoreObject gameStoreObject = getGameStoreObjectById(id);

        // Update the policy and save the object
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
