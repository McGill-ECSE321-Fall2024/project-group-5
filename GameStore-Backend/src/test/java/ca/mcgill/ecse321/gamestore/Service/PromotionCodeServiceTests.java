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

    @SuppressWarnings("null")
    @Test
    public void testCreateValidPromotionCode() {
    }

    @Test
    public void testReadPromotionCodeByValidId() {
    }

    @Test
    public void testReadPromotionCodeByInvalidId() {
    }
}