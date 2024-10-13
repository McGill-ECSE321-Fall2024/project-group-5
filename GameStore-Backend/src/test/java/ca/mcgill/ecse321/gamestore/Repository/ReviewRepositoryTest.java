package ca.mcgill.ecse321.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.Game;

import java.sql.Date;
import java.util.List;

@SpringBootTest
@Transactional
public class ReviewRepositoryTest {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@Autowired
	private GameRepository gameRepository;

	@AfterEach
	public void clearDatabase() {
		reviewRepository.deleteAll();
		customerAccountRepository.deleteAll();
		gameRepository.deleteAll();
	}

	@Test
	public void testSaveAndFindById() {
		// Create a CustomerAccount and save it
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("testuser@example.com");
		customerAccount.setName("Test User");
		customerAccountRepository.save(customerAccount);

		// Create a Game and save it
		Game game = new Game("Test Game", 50, "Test Description", Game.Category.Action, Game.GameConsole.PS5, null);
		gameRepository.save(game);

		// Create a Review
		Review review = new Review(Date.valueOf("2024-01-01"), "Great game!", 10, 2, 4.5f, false, customerAccount, game);
		reviewRepository.save(review);

		// Retrieve the review by its ID
		Review foundReview = reviewRepository.findById(review.getId()).orElse(null);

		// Ensure the review was saved and retrieved successfully
		assertNotNull(foundReview);
		assertEquals("Great game!", foundReview.getDescription());
		assertEquals(customerAccount.getId(), foundReview.getCustomerAccount().getId());
		assertEquals(game.getId(), foundReview.getGame().getId());
	}

	@Test
	public void testFindByCustomerAccount() {
		// Create a CustomerAccount and save it
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("findme@example.com");
		customerAccount.setName("Find Me");
		customerAccountRepository.save(customerAccount);

		// Create a Game and save it
		Game game = new Game("Another Test Game", 40, "Another Test Description", Game.Category.Action, Game.GameConsole.PS5, null);
		gameRepository.save(game);

		// Create a Review and associate it with the CustomerAccount and Game
		Review review = new Review(Date.valueOf("2024-01-01"), "Awesome game!", 12, 0, 5.0f, false, customerAccount, game);
		reviewRepository.save(review);

		// Find reviews by the CustomerAccount
		List<Review> foundReviews = reviewRepository.findByCustomerAccount(customerAccount);

		// Ensure the review was found and is associated with the correct customer
		assertNotNull(foundReviews);
		assertFalse(foundReviews.isEmpty());
		assertEquals(customerAccount.getId(), foundReviews.get(0).getCustomerAccount().getId());
	}

	@Test
	public void testFindByGame() {
		// Create a CustomerAccount and save it
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("gameuser@example.com");
		customerAccount.setName("Game User");
		customerAccountRepository.save(customerAccount);

		// Create a Game and save it
		Game game = new Game("Find Game", 60, "Description of Find Game", Game.Category.Shooter, Game.GameConsole.PC, null);
		gameRepository.save(game);

		// Create a Review associated with the Game
		Review review = new Review(Date.valueOf("2024-02-01"), "Great game!", 15, 1, 4.8f, false, customerAccount, game);
		reviewRepository.save(review);

		// Find reviews by the Game
		List<Review> foundReviews = reviewRepository.findByGame(game);

		// Ensure the review was found and is associated with the correct game
		assertNotNull(foundReviews);
		assertFalse(foundReviews.isEmpty());
		assertEquals(game.getId(), foundReviews.get(0).getGame().getId());
	}

	@Test
	public void testUpdateReview() {
		// Create a CustomerAccount and save it
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("updateme@example.com");
		customerAccount.setName("Update Me");
		customerAccountRepository.save(customerAccount);

		// Create a Game and save it
		Game game = new Game("Game to Update", 70, "Update Test Description", Game.Category.RPG, Game.GameConsole.NintendoSwitch, null);
		gameRepository.save(game);

		// Create a Review
		Review review = new Review(Date.valueOf("2024-03-01"), "Needs improvement.", 3, 10, 2.0f, false, customerAccount, game);
		reviewRepository.save(review);

		// Update the review description and rating
		Review foundReview = reviewRepository.findById(review.getId()).orElse(null);
		assertNotNull(foundReview);
		foundReview.setDescription("Actually, it's pretty good.");
		foundReview.setRating(4.0f);
		reviewRepository.save(foundReview);

		// Retrieve the updated review and verify the changes
		Review updatedReview = reviewRepository.findById(foundReview.getId()).orElse(null);
		assertNotNull(updatedReview);
		assertEquals("Actually, it's pretty good.", updatedReview.getDescription());
		assertEquals(4.0f, updatedReview.getRating());
	}

	@Test
	public void testDeleteReview() {
		// Create a CustomerAccount and save it
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("deleteme@example.com");
		customerAccount.setName("Delete Me");
		customerAccountRepository.save(customerAccount);

		// Create a Game and save it
		Game game = new Game("Game to Delete", 80, "Delete Test Description", Game.Category.Strategy, Game.GameConsole.XBoxSeriesX, null);
		gameRepository.save(game);

		// Create a Review
		Review review = new Review(Date.valueOf("2024-04-01"), "Decent strategy game.", 5, 2, 3.5f, false, customerAccount, game);
		reviewRepository.save(review);

		// Delete the review
		reviewRepository.delete(review);

		// Ensure the review was deleted
		Review deletedReview = reviewRepository.findById(review.getId()).orElse(null);
		assertNull(deletedReview);
	}
}
