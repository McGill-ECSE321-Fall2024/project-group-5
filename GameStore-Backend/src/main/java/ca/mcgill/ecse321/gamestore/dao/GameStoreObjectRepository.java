package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;
import org.springframework.data.repository.CrudRepository;

public interface GameStoreObjectRepository extends CrudRepository<GameStoreObject, Integer> {
    // Find a GameStoreObject by ID
    GameStoreObject findGameStoreObjectById(int id);

    // Delete a GameStoreObject by ID
    void deleteById(int id);
}
