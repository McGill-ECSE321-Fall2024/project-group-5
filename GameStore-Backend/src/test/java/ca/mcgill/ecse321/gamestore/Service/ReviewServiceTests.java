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

import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;
import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;

@SpringBootTest
public class ReviewServiceTests {
    @Mock
    private ReviewRepository repo;
    @InjectMocks
    private ReviewService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidReview() {
    }

    @Test
    public void testReadReviewByValidId() {
    }

    @Test
    public void testReadReviewByInvalidId() {
    }
}