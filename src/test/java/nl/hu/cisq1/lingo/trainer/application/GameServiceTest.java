package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    @Autowired
    GameService service;
    @Autowired
    SpringGameRepository repository;


    @Test
    void startGame ( ) {
    }

    @Test
    void startNewRound ( ) {
    }

    @Test
    void doGuess ( ) {
    }
}