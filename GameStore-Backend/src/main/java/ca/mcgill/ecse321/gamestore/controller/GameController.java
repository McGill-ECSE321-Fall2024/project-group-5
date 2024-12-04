package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.GameRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameResponseDto;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/new-game")
    public ResponseEntity<GameResponseDto> createGame(@RequestBody GameRequestDto gameRequestDto) {
        try {
            Game createdGame = gameService.addGame(
                gameRequestDto.getName(),
                gameRequestDto.getPrice(),
                gameRequestDto.getDescription(),
                Game.Category.valueOf(gameRequestDto.getCategory().name()),
                Game.GameConsole.valueOf(gameRequestDto.getGameConsole().name()),
                gameRequestDto.isInCatalog()
            );
            return new ResponseEntity<>(new GameResponseDto(createdGame), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GameResponseDto> getGameById(@PathVariable int id) {
        try {
            Game game = gameService.getGameById(id);
            return new ResponseEntity<>(new GameResponseDto(game), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GameResponseDto> updateGame(@PathVariable int id, @RequestBody GameRequestDto gameRequestDto) {
        try {
            Game updatedGame = gameService.updateGame(
                id,
                gameRequestDto.getName(),
                gameRequestDto.getPrice(),
                gameRequestDto.getDescription(),
                Game.Category.valueOf(gameRequestDto.getCategory().name()),
                Game.GameConsole.valueOf(gameRequestDto.getGameConsole().name()),
                gameRequestDto.isInCatalog()
            );
            return new ResponseEntity<>(new GameResponseDto(updatedGame), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable int id) {
        try {
            gameService.deleteGameById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/allgames")
    public ResponseEntity<List<GameResponseDto>> getAllGames() {
        List<GameResponseDto> games = gameService.listAllGames()
            .stream()
            .map(GameResponseDto::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }
}
