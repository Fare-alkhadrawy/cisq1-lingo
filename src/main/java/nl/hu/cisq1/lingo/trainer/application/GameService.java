package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class GameService {
    private final WordService word;
    private final SpringGameRepository repository;

    public GameService(WordService word, SpringGameRepository repository){
        this.repository = repository;
        this.word = word;
    }

}
