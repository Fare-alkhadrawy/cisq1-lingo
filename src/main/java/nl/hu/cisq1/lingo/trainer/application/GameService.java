package nl.hu.cisq1.lingo.trainer.application;



import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class GameService {
    private WordService word;
    private SpringGameRepository repository;

    public GameService(WordService word, SpringGameRepository repository) {
        this.word = word;
        this.repository = repository;
    }

    public Game startGame(){
        return repository.save(new Game(word.provideRandomWord(5)));
    }

    public Game startNewRound(long id) throws NotFoundException {
        Game game = repository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("game with id (%s) not founded",id)));
        game.startRound(word.provideRandomWord(game.wordLength()));
        repository.save(game);
        return game;
    }

    public Game doGuess(String attempt, long id) throws NotFoundException {
        Game game = repository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("game with id (%s) not founded",id)));;
        game.guessWord(attempt);
        repository.save(game);
        return game;
    }

}