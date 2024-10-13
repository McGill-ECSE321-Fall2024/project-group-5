package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Wishlist;

import org.springframework.data.repository.CrudRepository;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {

}