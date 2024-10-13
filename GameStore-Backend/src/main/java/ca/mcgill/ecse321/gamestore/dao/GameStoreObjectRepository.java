package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;
import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface GameStoreObjectRepository extends CrudRepository<GameStoreObject, Integer> {
    GameStoreObject getGameStoreObjectByIdIsNotNull();

    Integer deleteGameStoreObjectByIdNotNull();
}