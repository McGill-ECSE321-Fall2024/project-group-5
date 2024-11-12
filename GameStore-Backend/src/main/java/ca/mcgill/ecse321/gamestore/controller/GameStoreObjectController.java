package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamestore.dto.GameStoreObjectRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameStoreObjectResponseDto;
import ca.mcgill.ecse321.gamestore.model.GameStoreObject;
import ca.mcgill.ecse321.gamestore.service.GameStoreObjectService;

@RestController
@RequestMapping("/api/gamestoreobjects")
public class GameStoreObjectController {
    @Autowired
    private GameStoreObjectService gameStoreObjectService;

    @PostMapping("/create")
    public GameStoreObjectResponseDto createGameStoreObject(@RequestBody GameStoreObjectRequestDto requestDto) {
        GameStoreObject gameStoreObject = gameStoreObjectService.createGameStoreObject(
                requestDto.getName(),
                requestDto.getDescription(),
                requestDto.getPrice()
        );
        return new GameStoreObjectResponseDto(gameStoreObject);
    }

    @GetMapping("/{id}")
    public GameStoreObjectResponseDto getGameStoreObjectById(@PathVariable int id) {
        GameStoreObject gameStoreObject = gameStoreObjectService.getGameStoreObjectById(id);
        return new GameStoreObjectResponseDto(gameStoreObject);
    }

    @PutMapping("/{id}/update")
    public GameStoreObjectResponseDto updateGameStoreObject(@PathVariable int id, @RequestBody GameStoreObjectRequestDto requestDto) {
        GameStoreObject gameStoreObject = gameStoreObjectService.updateGameStoreObject(
                id,
                requestDto.getName(),
                requestDto.getDescription(),
                requestDto.getPrice()
        );
        return new GameStoreObjectResponseDto(gameStoreObject);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteGameStoreObject(@PathVariable int id) {
        gameStoreObjectService.deleteGameStoreObject(id);
    }
}
