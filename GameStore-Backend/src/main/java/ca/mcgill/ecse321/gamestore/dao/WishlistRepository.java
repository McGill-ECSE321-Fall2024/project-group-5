package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {


    List<Wishlist> findAll();


    Wishlist findByCustomerAccount_Id(int customerId);


    void deleteById(int id);
}
