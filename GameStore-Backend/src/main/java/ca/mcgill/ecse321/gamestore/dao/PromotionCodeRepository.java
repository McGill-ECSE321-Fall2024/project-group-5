package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;

import org.springframework.data.repository.CrudRepository;

public interface PromotionCodeRepository extends CrudRepository<PromotionCode, Integer> {
    // find PromotionCode by id
    PromotionCode getPromotionCodeById(int id);

    // delete PromotionCode by id
    void deletePromotionCodeById(int id);
}