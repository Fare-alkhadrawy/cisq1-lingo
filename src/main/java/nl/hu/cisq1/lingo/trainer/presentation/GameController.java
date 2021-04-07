package nl.hu.cisq1.lingo.trainer.presentation;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.exception.IllegalMoveException;
import nl.hu.cisq1.lingo.trainer.exception.InvalidAttemptException;
import nl.hu.cisq1.lingo.trainer.exception.RoundPlayingException;
import nl.hu.cisq1.lingo.trainer.presentation.DTOs.GameDTO;
import nl.hu.cisq1.lingo.trainer.presentation.DTOs.GuessDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    public GameDTO guessWord(@RequestBody GuessDTO guessDTO){
        try {
            return new GameDTO(service.doGuess(guessDTO.guess, guessDTO.id));
        }catch (InvalidAttemptException | IllegalMoveException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }catch (GameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PostMapping("/startRound/{id}")

    public GameDTO startRound(@PathVariable long id){
        try {
            return new GameDTO(service.startNewRound(id));
        }catch (RoundPlayingException | GameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
//    @PostMapping("/test")
//    public Game guess(@RequestBody GuessDTO guessDTO) throws NotFoundException {
//        return service.doGuess(guessDTO.guess, guessDTO.id);
//    }
}
