package ca.mcgill.ecse321.gamestore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.gamestore.model.Wishlist;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    // Custom query to find a Wishlist by its associated CustomerAccount
    Wishlist findByCustomerAccount(CustomerAccount customerAccount);
}
