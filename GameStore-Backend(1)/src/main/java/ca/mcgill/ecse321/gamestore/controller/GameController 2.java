package ca.mcgill.ecse321.gamestore.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.GameRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameResponseDto;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.service.GameService;


@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    public Game createGame(GameRequestDto gameRequestDto) {
        // Create a new Game entity from the DTO
        Game game = new Game();
        game.setName(gameRequestDto.getName());
        game.setPrice(gameRequestDto.getPrice());
        game.setDescription(gameRequestDto.getDescription());
        game.setCategory(gameRequestDto.getCategory());
        game.setGameConsole(gameRequestDto.getGameConsole());
        
        // Save the game to the database
        return gameRepository.save(game);
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

    // my vs code is struggling
    
    
    
    // Additional endpoint to get games by category (if needed)
    @GetMapping("/category/{category}")
    public ResponseEntity<List<GameResponseDto>> getGamesByCategory(@PathVariable String category) {
        List<Game> games = gameService.getCategory(category);
        List<GameResponseDto> gameDtos = games.stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

    // Additional endpoint to get games by console (if needed)
    @GetMapping("/console/{console}")
    public ResponseEntity<List<GameResponseDto>> getGamesConsole(@PathVariable String console) {
        List<Game> games = gameService.getGameConsole(console);
        List<GameResponseDto> gameDtos = games.stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }
}
