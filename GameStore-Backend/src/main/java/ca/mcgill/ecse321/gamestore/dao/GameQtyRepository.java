package ca.mcgill.ecse321.gamestore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321.gamestore.model.GameQty;
import ca.mcgill.ecse321.gamestore.model.Cart;
import ca.mcgill.ecse321.gamestore.model.Game;

import java.util.List;

@Repository
public interface GameQtyRepository extends JpaRepository<GameQty, Integer> {

    // Custom query to find all GameQty by Cart
    List<GameQty> findByCart(Cart cart);

    // Custom query to find all GameQty by Game
    List<GameQty> findByGame(Game game);
}
