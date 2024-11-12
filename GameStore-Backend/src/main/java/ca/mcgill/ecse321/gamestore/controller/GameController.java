package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gamestore.dto.GameRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameResponseDto;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.service.GameService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    // Endpoint to create a new game
    @PostMapping
    public ResponseEntity<GameResponseDto> createGame(@RequestBody GameRequestDto gameRequestDto) {
        Game createdGame = gameService.createGame(gameRequestDto);
        return new ResponseEntity<>(new GameResponseDto(createdGame), HttpStatus.CREATED);
    }

    // Endpoint to get a game by ID
    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDto> getGameById(@PathVariable int id) {
        Game game = gameService.getGameById(id);
        if (game != null) {
            return new ResponseEntity<>(new GameResponseDto(game), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint to get all games
    @GetMapping
    public ResponseEntity<List<GameResponseDto>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        List<GameResponseDto> gameDtos = games.stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

    // Endpoint to update a game by ID
    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDto> updateGame(@PathVariable int id, @RequestBody GameRequestDto gameRequestDto) {
        Game updatedGame = gameService.updateGame(id, gameRequestDto);
        if (updatedGame != null) {
            return new ResponseEntity<>(new GameResponseDto(updatedGame), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint to delete a game by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable int id) {
        boolean deleted = gameService.deleteGame(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Additional endpoint to get games by category (if needed)
    @GetMapping("/category/{category}")
    public ResponseEntity<List<GameResponseDto>> getGamesByCategory(@PathVariable String category) {
        List<Game> games = gameService.getGamesByCategory(category);
        List<GameResponseDto> gameDtos = games.stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

    // Additional endpoint to get games by console (if needed)
    @GetMapping("/console/{console}")
    public ResponseEntity<List<GameResponseDto>> getGamesByConsole(@PathVariable String console) {
        List<Game> games = gameService.getGamesByConsole(console);
        List<GameResponseDto> gameDtos = games.stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }
}
