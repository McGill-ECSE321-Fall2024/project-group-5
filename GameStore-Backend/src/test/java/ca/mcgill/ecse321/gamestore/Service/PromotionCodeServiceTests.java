package ca.mcgill.ecse321.gamestore.service;

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
        promotionCode.setDiscount(10);
        promotionCode.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(30)));

        when(repo.save(any(PromotionCode.class))).thenReturn(promotionCode);

        // Act
        PromotionCode created = service.createPromotionCode("PROMO10", 10, Date.valueOf(LocalDate.now().plusDays(30)));

        // Assert
        assertNotNull(created);
        assertEquals("PROMO10", created.getCode());
        assertEquals(10, created.getDiscount());
        verify(repo, times(1)).save(any(PromotionCode.class));
    }

    @Test
    public void testReadPromotionCodeByValidId() {
        // Arrange
        PromotionCode promotionCode = new PromotionCode();
        promotionCode.setId(1);
        promotionCode.setCode("PROMO10");
        promotionCode.setDiscount(10);
        when(repo.findById(1L)).thenReturn(Optional.of(promotionCode));

        // Act
        PromotionCode found = service.getPromotionCode(1L);

        // Assert
        assertNotNull(found);
        assertEquals(1, found.getId());
        assertEquals("PROMO10", found.getCode());
        assertEquals(10, found.getDiscount());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    public void testReadPromotionCodeByInvalidId() {
        // Arrange
        when(repo.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.getPromotionCode(999L);
        });
        verify(repo, times(1)).findById(999L);
    }
}
