package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import jakarta.transaction.Transactional;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

}