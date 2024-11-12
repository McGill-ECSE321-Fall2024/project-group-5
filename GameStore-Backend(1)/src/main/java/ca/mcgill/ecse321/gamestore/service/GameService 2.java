package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.Game.GameConsole;
import ca.mcgill.ecse321.gamestore.model.GameQty;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.dao.GameQtyRepository;
import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameQtyRepository gameQtyRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Find Game by Id
     * 
     * @param id - Game id
     * @return Game
     * @throws Exception
     * @carolinethom
     */
    public Game getGameByID(int id) throws Exception {
        Game game = gameRepository.findById(id);
        if (game == null) {
            throw new Exception("No game exists with this id");
        }
        return game;
    }

    /**
     * Find Game by name
     * 
     * @param name - Game name
     * @return Game
     * @throws Exception
     * @carolinethom
     */
    public Game getGameByName(String name) throws Exception {
        Game game = gameRepository.findByName(name);
        if (game == null) {
            throw new Exception("No game exists with this name");
        }
        return game;
    }

    /**
     * Add a new Game
     * 
     * @param name - Game name
     * @param price - Game price
     * @param description - Game description
     * @param category - Game category
     * @param gameConsolec - Game gameConsole
     * @param inCatalog - Game in catalog
     * @return updated gameRepository
     * @throws Exception
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
     * Delete a Game by ID
     * 
     * @param id - Game id
     * @return updated gameRepository
     * @throws Exception
     * @carolinethom
     */ 
    @Transactional
    public void deleteGame(int id) throws Exception {
        Game game = getGameByID(id);
        gameRepository.delete(game);
    }

    /**
     * List all Games
     * 
     * @param 
     * @return list of Games 
     * @throws Exception
     * @carolinethom
     */ 
    public List<Game> listAllGames() {
        return gameRepository.findAll();
    }

    /**
     * List Games by inCatalog status
     * 
     * @param inCatalog
     * @return list of Games 
     * @throws Exception
     * @carolinethom
     */ 
    public List<Game> listGamesInCatalog(boolean inCatalog) {
        return gameRepository.findByInCatalog(inCatalog);
    }

    /**
     * Add Review for a Game
     * 
     * @param id - Game id
     * @param description - Game description
     * @param likeCount - number of likes on review
     * @param dislikeCount - number of dislikes on review
     * @param rating - Game rating
     * @return updated reviewRepository 
     * @throws Exception
     * @carolinethom
     */ 
    @Transactional
    public Review addReview(int gameId, String description, int likeCount, int dislikeCount, float rating) throws Exception {
        Game game = getGameByID(gameId);
        Review review = new Review();
        review.setGame(game);
        review.setDescription(description);
        review.setLikeCount(likeCount);
        review.setDislikeCount(dislikeCount);
        review.setRating(rating);
        review.setDate(Date.valueOf(LocalDate.now()));
        return reviewRepository.save(review);
    }

    /**
     * Get Reviews for a Game
     * 
     * @param id - Game id
     * @return updated reviewRepository 
     * @throws Exception
     * @carolinethom
     */ 
    public List<Review> getReviewsByGame(int gameId) throws Exception {
        Game game = getGameByID(gameId);
        return reviewRepository.findByGame(game);
    }


}