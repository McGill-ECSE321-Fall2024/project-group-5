package ca.mcgill.ecse321.gamestore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamestore.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {

    // find Game by id
    Game findById(int id);

    // find Game by name 
    Game findByName(String name);

    // find Games by partial name
    List<Game> findByNameContaining(String name);

    // find Games by Category
    List<Game> findByCategory(Game.Category category);

    // find Games by GameConsole
    List<Game> findByGameConsole(Game.GameConsole gameConsole);

    // delete Game by id
    void deleteById(int id);
}
