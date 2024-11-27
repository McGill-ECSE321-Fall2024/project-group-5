package ca.mcgill.ecse321.gamestore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
     * @param name        - Game name
     * @param price       - Game price
     * @param description - Game description
     * @param category    - Game category
     * @param gameConsole - Game console
     * @param inCatalog   - Whether the game is in catalog
     * @return Saved Game
          * @throws Exception 
          * @throws IllegalArgumentException if any parameter is invalid
          * @carolinethom
          */
    @Transactional
    public Game addGame(String name, int price, String description, Category category, GameConsole gameConsole, boolean inCatalog) {
        validateGameDetails(name, price, description, category, gameConsole, inCatalog);
        // Check if a game with the same name already exists
        Game existingGame = gameRepository.findByName(name);
        if (existingGame != null) {
            throw new IllegalArgumentException("A game with this name already exists");
        }

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
     * @param id          - Game id
     * @param name        - New game name
     * @param price       - New game price
     * @param description - New game description
     * @param category    - New game category
     * @param gameConsole - New game console
     * @param inCatalog   - New catalog status
     * @return Updated Game
     * @throws Exception if game is not found or parameters are invalid
     * @carolinethom
     */
    @Transactional
    public Game updateGame(int id, String name, int price, String description, Category category, GameConsole gameConsole, boolean inCatalog) {
        validateGameDetails(name, price, description, category, gameConsole, inCatalog);
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
    public Game getGameById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
    
        Game game = gameRepository.findById(id);
        if (game == null) {
            throw new IllegalArgumentException("No game exists with this ID: " + id);
        } else {
            return game;
        }
    }
    

    /**
     * Find Game by name
     *
     * @param name - Game name
     * @return Game
     * @throws Exception if game not found or name is null/empty
     * @carolinethom
     */
    public Game getGameByName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Game name cannot be null or empty");
        }
    
        Game game = gameRepository.findByName(name);
        if (game == null) {
            throw new IllegalArgumentException("No game exists with this name: " + name);
        }
    
        return game;
    }
    

    /**
     * Find Games by partial name match
     *
     * @param partialName - Partial name for search
     * @return List of Games matching the partial name
     * @throws IllegalArgumentException if partial name is null/empty
     * @carolinethom
     */
    public List<Game> getGamesByPartialName(String partialName) {
        if (!StringUtils.hasText(partialName)) {
            throw new IllegalArgumentException("Partial name cannot be null or empty");
        }
        return gameRepository.findByNameContaining(partialName);
    }

    /**
     * Find Games by Category
     *
     * @param category - Category of the game
     * @return List of Games in the given category
     * @throws IllegalArgumentException if category is null
     * @carolinethom
     */
    public List<Game> getGamesByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return gameRepository.findByCategory(category);
    }

    /**
     * Find Games by GameConsole
     *
     * @param gameConsole - Console the game is played on
     * @return List of Games for the given console
     * @throws IllegalArgumentException if gameConsole is null
     * @carolinethom
     */
    public List<Game> getGamesByGameConsole(GameConsole gameConsole) {
        if (gameConsole == null) {
            throw new IllegalArgumentException("GameConsole cannot be null");
        }
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
    public void deleteGameById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("No game exists with this ID: " + id);
        }
        if (!gameRepository.existsById(id)) {
            throw new IllegalArgumentException("No game exists with this ID: " + id);
        }
        Game game = gameRepository.findById(id);
        gameRepository.delete(game);
    }

    /**
     * Helper method to validate game details
     *
     * @param name        - Game name
     * @param price       - Game price
     * @param description - Game description
     * @param category    - Game category
     * @param gameConsole - Game console
     * @throws IllegalArgumentException if any field is invalid
     */
    private void validateGameDetails(String name, int price, String description, Category category, GameConsole gameConsole, boolean inCatalog) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Game name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Game price cannot be negative");
        }
        if (description == null) {
            throw new IllegalArgumentException("Game description cannot be null or empty");
        }

        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (gameConsole == null) {
            throw new IllegalArgumentException("Game console cannot be null");
        }
        if (inCatalog == false) {
            throw new IllegalArgumentException("Game is not in catalog");
        }
    }
}
