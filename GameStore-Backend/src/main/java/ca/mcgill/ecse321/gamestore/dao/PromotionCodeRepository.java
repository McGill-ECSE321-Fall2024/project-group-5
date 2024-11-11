package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionCodeRepository extends CrudRepository<PromotionCode, Integer> {
    
    // Find a PromotionCode by its ID
    PromotionCode getPromotionCodeById(int id);

    // Delete a PromotionCode by its ID
    void deletePromotionCodeById(int id);
}
