package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;
import ca.mcgill.ecse321.gamestore.dao.GameStoreObjectRepository;
import jakarta.transaction.Transactional;

@Service
public class GameStoreObjectService {
    @Autowired
    private GameStoreObjectRepository gameStoreObjectRepository;

}