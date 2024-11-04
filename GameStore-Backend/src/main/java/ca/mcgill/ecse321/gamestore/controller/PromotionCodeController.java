package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.PromotionCodeRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PromotionCodeResponseDto;
import ca.mcgill.ecse321.gamestore.model.PromotionCode;
import ca.mcgill.ecse321.gamestore.service.PromotionCodeService;

@RestController
public class PromotionCodeController {
    @Autowired
    private PromotionCodeService promotionCodeService;

}