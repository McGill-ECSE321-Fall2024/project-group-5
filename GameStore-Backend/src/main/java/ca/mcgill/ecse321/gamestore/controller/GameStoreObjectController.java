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
    public ResponseEntity<GameStoreObjectResponseDto> createGameStoreObject(
            @RequestBody GameStoreObjectRequestDto requestDto) {
        try {
            GameStoreObject gameStoreObject = gameStoreObjectService.createGameStoreObject(requestDto.getPolicy());
            return ResponseEntity.status(HttpStatus.CREATED).body(new GameStoreObjectResponseDto(gameStoreObject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameStoreObjectResponseDto> getGameStoreObjectById(@PathVariable int id) {
        try {
            GameStoreObject gameStoreObject = gameStoreObjectService.getGameStoreObjectById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new GameStoreObjectResponseDto(gameStoreObject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GameStoreObjectResponseDto> updateGameStoreObject(@PathVariable int id,
            @RequestBody GameStoreObjectRequestDto requestDto) {
        try {
            GameStoreObject gameStoreObject = gameStoreObjectService.updateGameStoreObject(id, requestDto.getPolicy());
            return ResponseEntity.status(HttpStatus.OK).body(new GameStoreObjectResponseDto(gameStoreObject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGameStoreObject(@PathVariable int id) {
        try {
            gameStoreObjectService.deleteGameStoreObject(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
