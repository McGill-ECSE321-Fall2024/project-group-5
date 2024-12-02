package ca.mcgill.ecse321.gamestore.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.GameQtyRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameQtyResponseDto;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.Transaction;
import ca.mcgill.ecse321.gamestore.model.GameQty;
import ca.mcgill.ecse321.gamestore.service.GameQtyService;
import ca.mcgill.ecse321.gamestore.service.GameService;
import ca.mcgill.ecse321.gamestore.service.TransactionService;

@RestController
@RequestMapping("/api/game_qty")
public class GameQtyController {
    @Autowired
    private GameQtyService gameQtyService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private GameService gameService;

    /**
     * GET endpoint to retrieve a GameQty by its ID.
     * 
     * @param id the ID of the GameQty to retrieve
     * @return GameQtyResponseDto representation of the GameQty
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<GameQtyResponseDto> findGameQtyById(@PathVariable int id) {
        GameQty gameQty = gameQtyService.getGameQtyById(id);
        if (gameQty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(new GameQtyResponseDto(gameQty));
    }

    /**
     * GET endpoint to retrieve a list of GameQtys associated with a specific
     * transaction ID.
     * 
     * @param transactionId the ID of the transaction
     * @return a list of GameQtyResponseDto objects representing the GameQtys
     */
    @GetMapping("/get_by_transaction/{id}")
    public ResponseEntity<List<GameQtyResponseDto>> findGameQtiesByTransactionId(@PathVariable int transactionId) {
        List<GameQty> gameQties = gameQtyService.getGameQtiesByTransactionId(transactionId);
        List<GameQtyResponseDto> responseDtos = gameQties.stream()
                .filter(Objects::nonNull) // Remove null transactions
                .map(GameQtyResponseDto::new) // Use the constructor directly
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * POST endpoint to create a new GameQty entry.
     * 
     * @param gameQty the GameQtyRequestDto containing GameQty details
     * @return GameQtyResponseDto representing the newly created GameQty
     */
    @PostMapping("/create")
    public ResponseEntity<GameQtyResponseDto> createGameQty(@RequestBody GameQtyRequestDto gameQty) {
        Transaction transaction = transactionService.findTransactionById(gameQty.getTransaction().getTransactionId());
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Game game;
        try {
            game = gameService.getGameById(gameQty.getGame().getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        GameQty createdGameQty = gameQtyService.createGameQty(gameQty.getQty(), transaction, game);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameQtyResponseDto(createdGameQty));
    }

    /**
     * DELETE endpoint to remove a GameQty by its ID.
     * 
     * @param id the ID of the GameQty to delete
     * @return GameQtyResponseDto representation of the deleted GameQty
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GameQtyResponseDto> deleteGameQty(@PathVariable int id) {
        try {
            GameQty gameQty = gameQtyService.deleteGameQty(id);
            return ResponseEntity.ok(new GameQtyResponseDto(gameQty));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * PUT endpoint to update an existing GameQty entry.
     * 
     * @param gameQty the GameQtyResponseDto containing updated GameQty information
     * @return GameQtyResponseDto representing the updated GameQty
     */
    @PutMapping("/update")
    public ResponseEntity<GameQtyResponseDto> updateGameQty(@RequestBody GameQtyResponseDto gameQty) {
        GameQty updatedGameQty = gameQtyService.updateGameQty(gameQty.getId(), gameQty.getQty());
        if (updatedGameQty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(new GameQtyResponseDto(updatedGameQty));
    }
}