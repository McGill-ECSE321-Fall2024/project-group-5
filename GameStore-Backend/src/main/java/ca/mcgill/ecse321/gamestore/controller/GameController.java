package ca.mcgill.ecse321.gamestore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
     * Endpoint: /api/games/create
     */ 
    @PostMapping("/create")
    public GameResponseDto createGame(@RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.addGame(
            gameRequestDto.getName(),
            gameRequestDto.getPrice(),
            gameRequestDto.getDescription(),
            Game.Category.valueOf(gameRequestDto.getCategory().name()),  // Convert CategoryReqDto to Game.Category
            Game.GameConsole.valueOf(gameRequestDto.getGameConsole().name()),  // Convert GameConsoleReqDto to Game.GameConsole
            gameRequestDto.isInCatalog()
        );

        // Return a GameResponseDto wrapping the newly created Game
        return new GameResponseDto(game);
    }

    /**
     * GET: Retrieve a game by ID
     * Endpoint: /api/games/{id}
     */
    @GetMapping("/get/{id}")
    public GameResponseDto getGameById(@PathVariable int id) throws Exception {
        Game game = gameService.getGameById(id);
        if (game == null) {
            throw new Exception("Game not found with ID: " + id);
        }
        return new GameResponseDto(game);
    }

    /**
     * GET: Retrieve all games
     * Endpoint: /api/games
     */
    @GetMapping
    public List<GameResponseDto> getAllGames() {
        return gameService.listAllGames().stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * PUT: Update an existing game by ID
     * Endpoint: /api/games/{id}
     */
    @PutMapping("/update")
    public GameResponseDto updateGame(@RequestBody GameRequestDto gameRequestDto) throws Exception {
        Game updatedGame = gameService.updateGame(
            gameRequestDto.getGameID(),
            gameRequestDto.getName(),
            gameRequestDto.getPrice(),
            gameRequestDto.getDescription(),
            Game.Category.valueOf(gameRequestDto.getCategory().name()),  // Convert CategoryReqDto to Game.Category
            Game.GameConsole.valueOf(gameRequestDto.getGameConsole().name()),  // Convert GameConsoleReqDto to Game.GameConsole
            gameRequestDto.isInCatalog()
        );

        if (updatedGame == null) {
            throw new Exception("Game not found with ID");
        }
        return new GameResponseDto(updatedGame);
    }

    /**
     * DELETE: Delete a game by ID
     * Endpoint: /api/games/delete/{id}
     */
    @DeleteMapping("/delete/{id}")
    public GameResponseDto deleteGame(@PathVariable int id) throws Exception {
        Game game = gameService.getGameById(id); // Retrieve game details before deletion
        gameService.deleteGameById(id); // Perform the deletion
        return new GameResponseDto(game); // Return the deleted game details
    }


    /**
     * GET: Retrieve games by category
     * Endpoint: /api/games/category/{category}
     */
    @GetMapping("/category/{category}")
    public List<GameResponseDto> getGamesByCategory(@PathVariable Game.Category category) {
        return gameService.getGamesByCategory(category).stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * GET: Retrieve games by console
     * Endpoint: /api/games/console/{console}
     */
    @GetMapping("/console/{console}")
    public List<GameResponseDto> getGamesByConsole(@PathVariable Game.GameConsole console) {
        return gameService.getGamesByGameConsole(console).stream()
                .map(GameResponseDto::new)
                .collect(Collectors.toList());
    }
}
