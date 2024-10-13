package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Cart;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {

}