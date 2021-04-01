package nl.hu.cisq1.lingo.trainer.application;



import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class GameService {
    private final WordService word;
    private final SpringGameRepository repository;

    public GameService(WordService word, SpringGameRepository repository) {
        this.word = word;
        this.repository = repository;
    }

    public Game getGame(long id) throws NotFoundException {
         return repository.findById(id)
         .orElseThrow(()-> new NotFoundException(String.format("game with id (%s) not founded",id)));
    }


    public Game startGame(){
        return repository.save(new Game(word.provideRandomWord(5)));
    }

    public Game startNewRound(long id) throws NotFoundException {
        Game game = getGame(id);
        game.startRound(word.provideRandomWord(game.wordLength()));
        repository.save(game);
        return game;
    }

    public Game doGuess(String attempt, long id) throws NotFoundException {
        Game game = getGame(id);
        game.guessWord(attempt);
        repository.save(game);
        return game;
    }
}