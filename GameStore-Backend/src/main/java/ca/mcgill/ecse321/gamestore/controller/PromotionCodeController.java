package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.PromotionCodeRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PromotionCodeResponseDto;
import ca.mcgill.ecse321.gamestore.model.PromotionCode;
import ca.mcgill.ecse321.gamestore.service.PromotionCodeService;

@RestController
@RequestMapping("/promotion-code")
public class PromotionCodeController {

    @Autowired
    private PromotionCodeService promotionCodeService;

    @PostMapping("/create")
    public PromotionCodeResponseDto createPromotionCode(@RequestBody PromotionCodeRequestDto requestDto) {
        PromotionCode promotionCode = promotionCodeService.createPromotionCode(
                requestDto.getCode(),
                requestDto.getDiscount(), requestDto.getExpirationDate());
        return new PromotionCodeResponseDto(promotionCode);
    }

    @GetMapping("/{id}")
    public PromotionCodeResponseDto getPromotionCode(@PathVariable int id) {
        PromotionCode promotionCode = promotionCodeService.getPromotionCodeById(id);
        if (promotionCode != null) {
            return new PromotionCodeResponseDto(promotionCode);
        }
        return null; // Or you could handle this with an appropriate HTTP response status
    }

    @DeleteMapping("/delete/{id}")
    public String deletePromotionCode(@PathVariable int id) {
        boolean isDeleted = promotionCodeService.deletePromotionCode(id);
        if (isDeleted) {
            return "Promotion code with ID " + id + " deleted successfully.";
        } else {
            return "Promotion code with ID " + id + " not found.";
        }
    }
}
