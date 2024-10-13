package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;
import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface PromotionCodeRepository extends CrudRepository<PromotionCode, Integer> {
    PromotionCode getPromotionCodeById(int id);

    Integer deletePromotionCodeById(int id);
}