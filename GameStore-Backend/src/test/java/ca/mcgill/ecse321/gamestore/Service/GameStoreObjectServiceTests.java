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

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;
import ca.mcgill.ecse321.gamestore.service.GameStoreObjectService;
import ca.mcgill.ecse321.gamestore.dao.GameStoreObjectRepository;

@SpringBootTest
public class GameStoreObjectServiceTests {
    @Mock
    private GameStoreObjectRepository repo;
    @InjectMocks
    private GameStoreObjectService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidGameStoreObject() {
    }

    @Test
    public void testReadGameStoreObjectByValidId() {
    }

    @Test
    public void testReadGameStoreObjectByInvalidId() {
    }
}