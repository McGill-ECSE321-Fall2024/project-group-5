package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.gamestore.dto.GameStoreObjectRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameStoreObjectResponseDto;
import ca.mcgill.ecse321.gamestore.service.GameStoreObjectService;
import ca.mcgill.ecse321.gamestore.model.GameStoreObject;

@RestController
@RequestMapping("/api/gamestoreobjects")
public class GameStoreObjectController {

    @Autowired
    private GameStoreObjectService gameStoreObjectService;

    @PostMapping("/create")
    public ResponseEntity<Object> createGameStoreObject(@RequestBody GameStoreObjectRequestDto requestDto) {
        if (requestDto.getPolicy() == null || requestDto.getPolicy().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Policy cannot be empty");
        }

        try {
            GameStoreObject gameStoreObject = gameStoreObjectService.createGameStoreObject(requestDto.getPolicy());
            return ResponseEntity.status(HttpStatus.CREATED).body(new GameStoreObjectResponseDto(gameStoreObject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGameStoreObjectById(@PathVariable int id) {
        try {
            GameStoreObject gameStoreObject = gameStoreObjectService.getGameStoreObjectById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new GameStoreObjectResponseDto(gameStoreObject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GameStoreObject not found");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateGameStoreObject(
            @PathVariable int id,
            @RequestBody GameStoreObjectRequestDto requestDto) {
        if (requestDto.getPolicy() == null || requestDto.getPolicy().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Policy cannot be empty");
        }

        try {
            GameStoreObject gameStoreObject = gameStoreObjectService.updateGameStoreObject(id, requestDto.getPolicy());
            return ResponseEntity.status(HttpStatus.OK).body(new GameStoreObjectResponseDto(gameStoreObject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GameStoreObject not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteGameStoreObject(@PathVariable int id) {
        try {
            gameStoreObjectService.deleteGameStoreObject(id);
            return ResponseEntity.status(HttpStatus.OK).body("GameStoreObject deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GameStoreObject not found");
        }
    }
}
