package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {


    List<Review> findAll();


    List<Review> findByGame_Id(int gameId);


    List<Review> findByCustomerAccount_Id(int customerId);


    List<Review> findByReview_Id(int reviewId);


    void deleteById(int id);
}
