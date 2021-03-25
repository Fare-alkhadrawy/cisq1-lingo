package nl.hu.cisq1.lingo.trainer.presentation;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.DTOs.GameDTO;
import nl.hu.cisq1.lingo.trainer.presentation.DTOs.GuessDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/test")
    public Game guess(@RequestBody GuessDTO guessDTO) throws NotFoundException {
        return service.doGuess(guessDTO.guess, guessDTO.id);
    }
}
