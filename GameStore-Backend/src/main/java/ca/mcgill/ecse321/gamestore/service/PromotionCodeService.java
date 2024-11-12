package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;
import ca.mcgill.ecse321.gamestore.dao.PromotionCodeRepository;
import jakarta.transaction.Transactional;

@Service
public class PromotionCodeService {
    @Autowired
    private PromotionCodeRepository promotionCodeRepository;

    @Transactional
    public PromotionCode createPromotionCode(String name, int discountValue, Date expirationDate) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Promotion code name cannot be empty");
        }
        if (discountValue <= 0) {
            throw new IllegalArgumentException("Discount value must be greater than zero");
        }
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date must be a future date");
        }

        PromotionCode promotionCode = new PromotionCode();
        promotionCode.setCode(name);
        promotionCode.setPercentageValue(discountValue);
        promotionCode.setExpirationDate(expirationDate);

        return promotionCodeRepository.save(promotionCode);
    }

    @Transactional
    public PromotionCode getPromotionCodeById(int id) {
        Optional<PromotionCode> promotionCode = promotionCodeRepository.findById(id);
        return promotionCode.orElseThrow(() -> new IllegalArgumentException("Promotion code not found"));
    }

    @Transactional
    public PromotionCode updatePromotionCode(int id, String name, int discountValue, LocalDate expirationDate) {
        PromotionCode promotionCode = getPromotionCodeById(id);

        if (name != null && !name.isEmpty()) {
            promotionCode.setCode(name);
        }
        if (discountValue > 0) {
            promotionCode.setPercentageValue(discountValue);
        }
        if (expirationDate != null && expirationDate.isAfter(LocalDate.now())) {
            promotionCode.setExpirationDate(Date.valueOf(expirationDate));
        }

        return promotionCodeRepository.save(promotionCode);
    }

    @Transactional
    public boolean deletePromotionCode(int id) {
        if (!promotionCodeRepository.existsById(id)) {
            throw new IllegalArgumentException("Promotion code not found");
        }
        promotionCodeRepository.deleteById(id);
        return true;
    }
}
