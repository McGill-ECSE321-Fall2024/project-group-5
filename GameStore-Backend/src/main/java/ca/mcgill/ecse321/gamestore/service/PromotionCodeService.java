package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;
import ca.mcgill.ecse321.gamestore.dao.PromotionCodeRepository;
import jakarta.transaction.Transactional;

@Service
public class PromotionCodeService {
    @Autowired
    private PromotionCodeRepository promotionCodeRepository;

}