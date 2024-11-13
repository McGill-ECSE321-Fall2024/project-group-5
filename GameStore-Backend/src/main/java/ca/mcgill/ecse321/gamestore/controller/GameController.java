package ca.mcgill.ecse321.gamestore.controller;

import java.util.List;
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

import ca.mcgill.ecse321.gamestore.dto.GameRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameResponseDto;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.service.GameService;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * POST: Create a new game
     * Endpoint: /api/games
     */
    @PostMapping("/newgame")
    public ResponseEntity<GameResponseDto> createGame(@RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.addGame(
            gameRequestDto.getName(),
            gameRequestDto.getPrice(),
            gameRequestDto.getDescription(),
            Game.Category.valueOf(gameRequestDto.getCategory().name()),
            Game.GameConsole.valueOf(gameRequestDto.getGameConsole().name()),
            gameRequestDto.isInCatalog()
        );
        return new ResponseEntity<>(new GameResponseDto(game), HttpStatus.CREATED);
    }

    /**
     * GET: Retrieve a game by ID
     * Endpoint: /api/games/{id}
    */
    @GetMapping("/get/{id}")
    public ResponseEntity<GameResponseDto> getGameById(@PathVariable int id) {
        try {
            Game game = gameService.getGameById(id);
            return new ResponseEntity<>(new GameResponseDto(game), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Log the stack trace for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 if something goes wrong
        }
    }
          

    /**
     * GET: Retrieve all games
     * Endpoint: /api/games
     */
    @GetMapping("/get/allgames")
    public ResponseEntity<List<GameResponseDto>> getAllGames() {
        List<Game> games = gameService.listAllGames();
        List<GameResponseDto> gameDtos = games.stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

    /**
     * PUT: Update an existing game by ID
     * Endpoint: /api/games/{id}
     */
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
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE: Delete a game by ID
     * Endpoint: /api/games/{id}
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable int id) {
        try {
            gameService.deleteGameById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET: Retrieve games by category
     * Endpoint: /api/games/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<GameResponseDto>> getGamesByCategory(@PathVariable Game.Category category) {
        List<Game> games = gameService.getGamesByCategory(category);
        List<GameResponseDto> gameDtos = games.stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

    /**
     * GET: Retrieve games by console
     * Endpoint: /api/games/console/{console}
     */
    @GetMapping("/console/{console}")
    public ResponseEntity<List<GameResponseDto>> getGamesByConsole(@PathVariable Game.GameConsole console) {
        List<Game> games = gameService.getGamesByGameConsole(console);
        List<GameResponseDto> gameDtos = games.stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }
}
