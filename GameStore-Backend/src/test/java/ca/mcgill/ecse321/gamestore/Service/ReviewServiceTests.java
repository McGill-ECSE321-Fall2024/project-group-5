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

import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;
import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;
import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private ReviewRepository repo;

    @InjectMocks
    private ReviewService service;

    @Test
    public void testCreateValidReview() {
        ReviewRequestDto request = new ReviewRequestDto();
        request.setName("Great Game!");

        Review savedReview = new Review();
        savedReview.setId(1);
        savedReview.setName(request.getName());
        savedReview.setDate(Date.valueOf(LocalDate.now()));

        when(repo.save(any(Review.class))).thenReturn(savedReview);

        Review result = service.createReview(request);

        assertNotNull(result);
        assertEquals("Great Game!", result.getName());
        assertNotNull(result.getDate());
        assertEquals(1, result.getId());

        verify(repo, times(1)).save(any(Review.class));
    }

    @Test
    public void testReadReviewByValidId()  {
        Review mockReview = new Review();
        mockReview.setId(1);
        mockReview.setName("Amazing Game!");
        mockReview.setDate(Date.valueOf(LocalDate.now()));

        when(repo.findById(1)).thenReturn(Optional.of(mockReview));

        Review result = service.getReviewById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Amazing Game!", result.getName());
        assertNotNull(result.getDate());

        verify(repo, times(1)).findById(1);
    }

    @Test
    public void testReadReviewByInvalidId() {
        when(repo.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getReviewById(999);
        });

        assertEquals("Review not found", exception.getMessage());

        verify(repo, times(1)).findById(999);
    }
}
