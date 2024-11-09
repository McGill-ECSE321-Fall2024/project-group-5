package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;

import org.springframework.data.repository.CrudRepository;

public interface GameStoreObjectRepository extends CrudRepository<GameStoreObject, Integer> {
    // Find GameStoreObject instance
    GameStoreObject getGameStoreObjectByIdIsNotNull();

    // delete GameStoreObject by id
    void deleteGameStoreObjectById(int Id);
}