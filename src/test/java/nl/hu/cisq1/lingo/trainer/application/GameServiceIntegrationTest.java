package nl.hu.cisq1.lingo.trainer.application;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.RoundStatus;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class GameServiceIntegrationTest {

    @Autowired
    GameService service;
    @Autowired
    SpringGameRepository repository;


    @DisplayName("test throw exception if the game not founded")
    @Test
    void getGameTestFail(){
        assertThrows(GameNotFoundException.class,()-> service.getGame((long) 0));
    }

    @DisplayName("test if the first word has 5 letter in new game")
    @Test
    void startGameWordLength ( ){
        Game game = service.startGame();
        String wordToGuess = game.getLastRound().getWordToGuess();
        assertEquals(5, wordToGuess.length());
        service.doGuess(wordToGuess, game.getGameId());
        service.startNewRound(game.getGameId());
        wordToGuess = game.getLastRound().getWordToGuess();
        assertEquals(6, wordToGuess.length());
    }



    @Test
    @DisplayName("test if the service make a new game")
    void startGame ( ) {
        Game game = service.startGame();
        assertNotNull(game);
        assertEquals(1, game.getRounds().size());
    }

    @Test
    @DisplayName("test make a new round")
    void startNewRound ( ){
        WordService wordService= Mockito.mock(WordService.class);
        Mockito.when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("appel");
        GameService service1 = new GameService(wordService, repository);
        Game game = service1.startGame();
        service1.doGuess("appel", game.getGameId());
        assertEquals(2, service1.startNewRound(game.getGameId()).getRounds().size());
    }

    @Test
    @DisplayName("test if the word guessed")
    void wordGuessedTest ( ){
        Game game = service.startGame();
        String wordToGuess = game.getLastRound().getWordToGuess();
        service.doGuess(wordToGuess, game.getGameId());
        assertEquals(RoundStatus.WIN, game.getLastRound().getRoundStatus());

    }
}
