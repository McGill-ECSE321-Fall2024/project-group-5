package ca.mcgill.ecse321.gamestore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.Game;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Find all reviews for a specific customer account
    List<Review> findByCustomerAccount(CustomerAccount customerAccount);

    // Find all reviews for a specific game
    List<Review> findByGame(Game game);

    // Find all reviews that are replies to a specific review
    List<Review> findByReview(Review review);
}
