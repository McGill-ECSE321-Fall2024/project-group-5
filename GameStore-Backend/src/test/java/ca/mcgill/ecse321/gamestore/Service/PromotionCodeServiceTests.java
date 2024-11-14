package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;
import ca.mcgill.ecse321.gamestore.service.PromotionCodeService;
import ca.mcgill.ecse321.gamestore.dao.PromotionCodeRepository;

@SpringBootTest
public class PromotionCodeServiceTests {

    @Mock
    private PromotionCodeRepository repo;

    @InjectMocks
    private PromotionCodeService service;

    @Test
    public void testCreateValidPromotionCode() {
        // Arrange
        PromotionCode promotionCode = new PromotionCode();
        promotionCode.setCode("PROMO10");
        promotionCode.setPercentageValue(10);
        promotionCode.setExpirationDate(Date.valueOf(LocalDate.now().plusDays(30)));

        when(repo.save(any(PromotionCode.class))).thenReturn(promotionCode);

        // Act
        PromotionCode created = service.createPromotionCode("PROMO10", 10, Date.valueOf(LocalDate.now().plusDays(30)));

        // Assert
        assertNotNull(created);
        assertEquals("PROMO10", created.getCode());
        assertEquals(10, created.getPercentageValue());
        verify(repo, times(1)).save(any(PromotionCode.class));
    }

    @Test
    public void testReadPromotionCodeByValidId() {
        // Arrange
        PromotionCode promotionCode = new PromotionCode();
        promotionCode.setCode("PROMO10");
        promotionCode.setPercentageValue(10);
        when(repo.findById(promotionCode.getId())).thenReturn(Optional.of(promotionCode));

        // Act
        PromotionCode found = service.getPromotionCodeById(promotionCode.getId());

        // Assert
        assertNotNull(found);
        assertEquals(promotionCode.getId(), found.getId());
        assertEquals("PROMO10", found.getCode());
        assertEquals(10, found.getPercentageValue());
        verify(repo, times(1)).findById(promotionCode.getId());
    }

    @Test
    public void testReadPromotionCodeByInvalidId() {
        // Arrange
        when(repo.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.getPromotionCodeById(999);
        });
        verify(repo, times(1)).findById(999);
    }
}
