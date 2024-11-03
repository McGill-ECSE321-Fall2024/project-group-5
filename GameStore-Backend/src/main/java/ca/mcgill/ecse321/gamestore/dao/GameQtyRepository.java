package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.GameQty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameQtyRepository extends CrudRepository<GameQty, Integer> {

    List<GameQty> findByGame_Id(int gameId);

    List<GameQty> findByTransaction_TransactionId(int transactionId);

    void deleteById(int id);
}
