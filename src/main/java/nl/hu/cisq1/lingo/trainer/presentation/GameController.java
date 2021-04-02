package nl.hu.cisq1.lingo.trainer.presentation;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.DTOs.GameDTO;
import nl.hu.cisq1.lingo.trainer.presentation.DTOs.GuessDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/game")
public class GameController {
    private final GameService service;

    public GameController(GameService service){
        this.service = service;
    }


    @PostMapping
    public GameDTO startGame(){
        return new GameDTO(service.startGame());
    }

    @PostMapping("/guess")

    public GameDTO guessWord(@RequestBody GuessDTO guessDTO) throws NotFoundException {
        return new GameDTO(service.doGuess(guessDTO.guess, guessDTO.id));
    }
    @PostMapping("/startRound/{id}")

    public GameDTO startRound(@PathVariable long id) throws NotFoundException {
        return new GameDTO(service.startNewRound(id));
    }
    @PostMapping("/test")
    public Game guess(@RequestBody GuessDTO guessDTO) throws NotFoundException {
        return service.doGuess(guessDTO.guess, guessDTO.id);
    }
}
