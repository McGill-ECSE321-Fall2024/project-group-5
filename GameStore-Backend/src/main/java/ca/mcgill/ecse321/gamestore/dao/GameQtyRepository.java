package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.GameQty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameQtyRepository extends CrudRepository<GameQty, Integer> {

    List<GameQty> findByGame_Id(int gameId);

    List<GameQty> findByCart_Id(int cartId);

    void deleteById(int id);
}
