package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.GameQty;

import org.springframework.data.repository.CrudRepository;

public interface GameQtyRepository extends CrudRepository<GameQty, Integer> {

}