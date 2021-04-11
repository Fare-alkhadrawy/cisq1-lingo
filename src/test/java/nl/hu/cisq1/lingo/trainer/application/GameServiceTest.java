package nl.hu.cisq1.lingo.trainer.application;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.RoundStatus;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.exception.IllegalMoveException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


class GameServiceTest {
    private GameService service;
    private Game game;
    @BeforeEach
    void beforeEach(){
        WordService word = Mockito.mock(WordService.class);
        SpringGameRepository repository = mock(SpringGameRepository.class);
        when(word.provideRandomWord(anyInt())).thenReturn("appel");
        game = new Game(word.provideRandomWord(6));
        when(repository.findById(2L)).thenReturn(java.util.Optional.of(game));
        when(repository.save(any(Game.class))).thenReturn(game);
        service = new GameService(word, repository);
    }

    @Test
    @DisplayName("Start new game")
    void startNewGameTest() {
        assertEquals(game,service.startGame());
    }
    @Test
    @DisplayName("Test if the word guessed")
    void wonRoundTest(){
        assertEquals(RoundStatus.WIN, service.doGuess("appel", 2L).getLastRound().getRoundStatus());
    }
    @Test
    @DisplayName("Test if the game is lost")
    void lostGameTest(){
        for (int i = 1 ; i < 5; i++)
            service.doGuess("aapel", 2L);
        assertEquals(GameStatus.LOST, service.doGuess("aapel", 2L).getGameStatus());
    }
    @Test
    @DisplayName("Test if the game Ended")
    void gameEndTest(){
        for (int i = 1 ; i < 6; i++)
            service.doGuess("aapel", 2L);
        assertThrows(IllegalMoveException.class, ()->service.doGuess("aapel", 2L));
    }

    @Test
    @DisplayName("Test if the new round started")
    void startNewRoundTest(){
            service.doGuess("appel", 2L);
            assertEquals(2,service.startNewRound(2L).getRounds().size());
    }
    @Test
    @DisplayName("Test if the game not founded")
    void gameNotFoundedTest(){
        assertThrows(GameNotFoundException.class,()->service.getGame(4L));
    }

    @Test
    @DisplayName("Test if the game founded")
    void gameFoundedTest(){
        assertEquals(game, service.getGame(2L));
    }
}