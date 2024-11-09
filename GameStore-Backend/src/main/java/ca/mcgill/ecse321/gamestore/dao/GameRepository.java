package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {

    Game findById(int id);

    List<Game> findByCategory(Game.Category category);

    List<Game> findByGameConsole(Game.GameConsole gameConsole);

    void deleteById(int id);
}
