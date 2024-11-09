package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;

import org.springframework.data.repository.CrudRepository;

public interface PromotionCodeRepository extends CrudRepository<PromotionCode, Integer> {
    PromotionCode getPromotionCodeById(int id);

    void deletePromotionCodeById(int id);
}