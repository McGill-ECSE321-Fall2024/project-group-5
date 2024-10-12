package ca.mcgill.ecse321.gamestore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.Game.Category;
import ca.mcgill.ecse321.gamestore.model.Game.GameConsole;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    // Custom query to find games by category
    List<Game> findByCategory(Category category);

    // Custom query to find games by console
    List<Game> findByGameConsole(GameConsole gameConsole);

    // Custom query to find games by name
    List<Game> findByName(String name);
}
