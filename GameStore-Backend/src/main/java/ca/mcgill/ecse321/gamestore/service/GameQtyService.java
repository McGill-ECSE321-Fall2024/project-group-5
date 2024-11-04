package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.GameQty;
import ca.mcgill.ecse321.gamestore.dao.GameQtyRepository;
import jakarta.transaction.Transactional;

@Service
public class GameQtyService {
    @Autowired
    private GameQtyRepository gameQtyRepository;

}