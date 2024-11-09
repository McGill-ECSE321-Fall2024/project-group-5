package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    // find Reviews by Game id
    List<Review> findByGame_Id(int gameId);

    // find Reviews by CustomerAccount id
    List<Review> findByCustomerAccount_Id(int customerId);

    // find Replies to Review by Review id
    List<Review> findByReview_Id(int reviewId);

    // delete Review by id
    void deleteById(int id);
}
