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
    public GameStoreObject createGameStoreObject(String name, String description, double price, String policy) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        GameStoreObject gameStoreObject = new GameStoreObject();
        gameStoreObject.setName(name);
        gameStoreObject.setDescription(description);
        gameStoreObject.setPrice(price);
        gameStoreObject.setPolicy(policy);

        return gameStoreObjectRepository.save(gameStoreObject);
    }

    @Transactional
    public GameStoreObject getGameStoreObjectById(int id) {
        Optional<GameStoreObject> gameStoreObject = gameStoreObjectRepository.findById(id);
        return gameStoreObject.orElseThrow(() -> new IllegalArgumentException("GameStoreObject not found"));
    }

    @Transactional
    public GameStoreObject updateGameStoreObject(int id, String policy) {
        GameStoreObject gameStoreObject = getGameStoreObjectById(id);

        if (name != null && !name.isEmpty()) {
            gameStoreObject.setName(name);
        }
        if (description != null) {
            gameStoreObject.setDescription(description);
        }
        if (price > 0) {
            gameStoreObject.setPrice(price);
        }
        if (policy != null) {
            gameStoreObject.setPolicy(policy);
        }
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
