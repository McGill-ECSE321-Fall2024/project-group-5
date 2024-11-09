package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {

    // find Game by id
    Game findById(int id);

    // find Games by Category
    List<Game> findByCategory(Game.Category category);

    // find Games by GameConsole
    List<Game> findByGameConsole(Game.GameConsole gameConsole);

    // delete Game by id
    void deleteById(int id);
}
