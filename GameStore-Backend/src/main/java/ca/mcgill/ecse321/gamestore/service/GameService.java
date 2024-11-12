package ca.mcgill.ecse321.gamestore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.Game.Category;
import ca.mcgill.ecse321.gamestore.model.Game.GameConsole;
import jakarta.transaction.Transactional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Add a new Game
     *
     * @param name - Game name
     * @param price - Game price
     * @param description - Game description
     * @param category - Game category
     * @param gameConsole - Game console
     * @param inCatalog - Whether the game is in catalog
     * @return Saved Game
     * @carolinethom
     */
    @Transactional
    public Game addGame(String name, int price, String description, Category category, GameConsole gameConsole, boolean inCatalog) {
        Game game = new Game();
        game.setName(name);
        game.setPrice(price);
        game.setDescription(description);
        game.setCategory(category);
        game.setGameConsole(gameConsole);
        game.setInCatalog(inCatalog);
        return gameRepository.save(game);
    }

    /**
     * Update an existing Game by ID
     *
     * @param id - Game id
     * @param name - New game name
     * @param price - New game price
     * @param description - New game description
     * @param category - New game category
     * @param gameConsole - New game console
     * @param inCatalog - New catalog status
     * @return Updated Game
     * @throws Exception if game is not found
     * @carolinethom
     */
    @Transactional
    public Game updateGame(int id, String name, int price, String description, Category category, GameConsole gameConsole, boolean inCatalog) throws Exception {
        Game game = getGameById(id);
        game.setName(name);
        game.setPrice(price);
        game.setDescription(description);
        game.setCategory(category);
        game.setGameConsole(gameConsole);
        game.setInCatalog(inCatalog);
        return gameRepository.save(game);
    }

    /**
     * Find Game by ID
     *
     * @param id - Game id
     * @return Game
     * @throws Exception if game not found
     * @carolinethom
     */
    public Game getGameById(int id) throws Exception {
        Game game = gameRepository.findById(id);
        if (game == null) {
            throw new Exception("No game exists with this ID: " + id);
        }
        return game;
    }

    /**
     * Find Game by name
     *
     * @param name - Game name
     * @return Game
     * @throws Exception if game not found
     * @carolinethom
     */
    public Game getGameByName(String name) throws Exception {
        Game game = gameRepository.findByName(name);
        if (game == null) {
            throw new Exception("No game exists with this name: " + name);
        }
        return game;
    }

    /**
     * Find Games by partial name match
     *
     * @param partialName - Partial name for search
     * @return List of Games matching the partial name
     * @carolinethom
     */
    public List<Game> getGamesByPartialName(String partialName) {
        return gameRepository.findByNameContaining(partialName);
    }

    /**
     * Find Games by Category
     *
     * @param category - Category of the game
     * @return List of Games in the given category
     * @carolinethom
     */
    public List<Game> getGamesByCategory(Game.Category category) {
        return gameRepository.findByCategory(category);
    }

    /**
     * Find Games by GameConsole
     *
     * @param gameConsole - Console the game is played on
     * @return List of Games for the given console
     * @carolinethom
     */
    public List<Game> getGamesByGameConsole(Game.GameConsole gameConsole) {
        return gameRepository.findByGameConsole(gameConsole);
    }

    /**
     * List all Games
     *
     * @return List of all Games
     * @carolinethom
     */
    public List<Game> listAllGames() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    /**
     * Delete a Game by ID
     *
     * @param id - Game id
     * @throws Exception if game is not found
     * @carolinethom
     */
    @Transactional
    public void deleteGameById(int id) throws Exception {
        Game game = getGameById(id); // Ensure game exists before deleting
        gameRepository.deleteById(id);
    }
}
