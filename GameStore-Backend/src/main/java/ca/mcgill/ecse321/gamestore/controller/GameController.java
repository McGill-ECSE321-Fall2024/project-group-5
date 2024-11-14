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
     * Endpoint: /api/games/newgame
     */
    @PostMapping("/newgame")
    public ResponseEntity<String> createGame(@RequestBody GameRequestDto gameRequestDto) {
        // Validate each field before calling the service
        if (gameRequestDto.getName() == null || gameRequestDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Game name cannot be null or empty");
        }

        if (gameRequestDto.getPrice() < 0) {
            return ResponseEntity.badRequest().body("Game price cannot be negative");
        }

        if (gameRequestDto.getDescription() == null || gameRequestDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().body("Game description cannot be null or empty");
        }

        if (gameRequestDto.getCategory() == null) {
            return ResponseEntity.badRequest().body("Game category cannot be null");
        }

        if (gameRequestDto.getGameConsole() == null) {
            return ResponseEntity.badRequest().body("Game console cannot be null");
        }

        try {
            // If all checks pass, create the game
            gameService.addGame(
                gameRequestDto.getName(),
                gameRequestDto.getPrice(),
                gameRequestDto.getDescription(),
                Game.Category.valueOf(gameRequestDto.getCategory().name()),
                Game.GameConsole.valueOf(gameRequestDto.getGameConsole().name()),
                gameRequestDto.isInCatalog()
            );

          

            // Return the created game with status CREATED (201)
            return new ResponseEntity<>("Game created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // If the service layer throws an exception, return BAD_REQUEST with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected errors and return INTERNAL_SERVER_ERROR
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


        /**
     * GET: Retrieve a game by ID
     * Endpoint: /api/games/get/{id}
    * @throws Exception 
    */
    @GetMapping("/get/{id}")
    public ResponseEntity<GameResponseDto> getGameById(@PathVariable int id) throws Exception {
    // Game game = gameService.getGameById(id);
    // return new ResponseEntity<>(new GameResponseDto(game), HttpStatus.OK);
    try {
        if (id <=0 ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Game game = gameService.getGameById(id);
        if (game == null) {  // Check if the game is null and return NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new GameResponseDto(game), HttpStatus.OK);
    } catch (Exception e) {
        // e.printStackTrace();  // Log stack trace for debugging
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 if something goes wrong
        }
    }
   

    /**
     * GET: Retrieve all games
     * Endpoint: /api/games/get/allgames
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
     * Endpoint: /api/games//update/{id}
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGame(@PathVariable int id, @RequestBody GameRequestDto gameRequestDto) {
        // Validate each field before calling the service
        if (gameRequestDto.getName() == null || gameRequestDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Game name cannot be null or empty");
        }

        if (gameRequestDto.getPrice() < 0) {
            return ResponseEntity.badRequest().body("Price cannot be negative");
        }

        if (gameRequestDto.getDescription() == null || gameRequestDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().body("Game description cannot be null or empty");
        }

        if (gameRequestDto.getCategory() == null) {
            return ResponseEntity.badRequest().body("Game category cannot be null");
        }

        if (gameRequestDto.getGameConsole() == null) {
            return ResponseEntity.badRequest().body("Game console cannot be null");
        }

        try {
            // If all checks pass, update the game
            gameService.updateGame(
                id,
                gameRequestDto.getName(),
                gameRequestDto.getPrice(),
                gameRequestDto.getDescription(),
                Game.Category.valueOf(gameRequestDto.getCategory().name()),
                Game.GameConsole.valueOf(gameRequestDto.getGameConsole().name()),
                gameRequestDto.isInCatalog()
            );

            // Return the updated game with status OK (200)
            return new ResponseEntity<>("Game updated successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // If the service layer throws an exception, return BAD_REQUEST with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected errors and return NOT_FOUND (404) if something goes wrong
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * DELETE: Delete a game by ID
     * Endpoint: /api/games/delete/{id}
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
