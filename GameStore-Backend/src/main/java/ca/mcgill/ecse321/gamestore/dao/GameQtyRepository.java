package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.GameQty;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameQtyRepository extends CrudRepository<GameQty, Integer> {
    // find GameQties by Game id
    List<GameQty> findByGame_Id(int gameId);

    // find GameQties by Transaction id
    List<GameQty> findByTransaction_TransactionId(int transactionId);

    // find GameQty by id
    GameQty findById(int id);

    // delete GameQty by id
    void deleteById(int id);
}
