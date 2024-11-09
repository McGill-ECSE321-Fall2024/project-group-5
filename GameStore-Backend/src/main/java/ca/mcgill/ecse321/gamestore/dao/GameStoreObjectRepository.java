package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;

import org.springframework.data.repository.CrudRepository;

public interface GameStoreObjectRepository extends CrudRepository<GameStoreObject, Integer> {
    GameStoreObject getGameStoreObjectByIdIsNotNull();

    void deleteGameStoreObjectById(int Id);
}