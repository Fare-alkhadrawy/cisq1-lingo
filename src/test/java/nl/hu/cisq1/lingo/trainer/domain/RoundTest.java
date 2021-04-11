package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.exception.IllegalMoveException;
import nl.hu.cisq1.lingo.trainer.exception.InvalidAttemptException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    @DisplayName("Can not guessed when the wordToGuess lenght not like attempt lenght ")
    void ongeldigGuess(){
        Round round = new Round("word");
        String attempt = "woord";
        assertThrows( InvalidAttemptException.class,
                ()-> round.guessWord(attempt));
    }

    @Test
    @DisplayName("Can not guessed when the game ended")
    void ongeldigMove(){
        Round round = new Round("word");
        for (int i = 0 ; i < 5 ; i++)
            round.guessWord("bbbb");
        assertThrows( IllegalMoveException.class,
                ()-> round.guessWord("word"));
        round.setRoundStatus(RoundStatus.PLAYING);
        assertThrows( IllegalMoveException.class,
                ()-> round.guessWord("word"));
    }

    @Test
    @DisplayName("Can not guessed when the game ended")
    void ongeldigMove2(){
        Round round = new Round("word");
        for (int i = 0 ; i < 4 ; i++)
            round.guessWord("bbbb");
        round.setRoundStatus(RoundStatus.LOSE);
        assertThrows( IllegalMoveException.class,
                ()-> round.guessWord("bbbb"));
    }



    @Test
    @DisplayName("the game ended when try wrong 5 time")
    void losRoundTest(){
        Round round = new Round("word");
        for (int i = 0 ; i < 5 ; i++)
            round.guessWord("bbbb");
       assertEquals(RoundStatus.LOSE, round.getRoundStatus());
    }



    @ParameterizedTest(name = "Test #{index} | {0} | {1} | {2}| {3}")
    @MethodSource("guessWordFeedbackTesten")
    @DisplayName("test new guess feedback output")
    void guessWordTest(Feedback feedback, String attempt) {
        Round round = new Round("woord");
        assertEquals(feedback, round.guessWord(attempt));


    }

    static Stream<Arguments> guessWordFeedbackTesten() {
        return Stream.of(
                Arguments.of(new Feedback("wordd", List.of(Mark.CORRECT,Mark.CORRECT,Mark.PRESENT,Mark.PRESENT,Mark.CORRECT)),
                        "wordd"),
                Arguments.of(new Feedback("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT)),
                        "woord"),
                Arguments.of(new Feedback("boord", List.of(Mark.ABSENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT)),
                        "boord"),
                Arguments.of(new Feedback("talen", List.of(Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT)),
                        "talen")
        );
    }

    @Test
    @DisplayName("test when the word guessed ")
    void roundIsGuessed(){
        Round round = new Round("word");
        round.guessWord("word");
        assertEquals(RoundStatus.WIN, round.getRoundStatus());
    }

    @Test
    @DisplayName("test when the player lost ")
    void roundIsLost(){
        Round round = new Round("word");
        for (int i = 0 ; i < 5; i++) {
            round.guessWord("wood");
        }
        assertEquals(RoundStatus.LOSE, round.getRoundStatus());
    }
    @Test
    @DisplayName("Test the first hint when the round begon")
    void firstHintTest(){
        Round round = new Round("word");
        Feedback feedback = new Feedback("w...",List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT));
        Feedback feedback1 = round.firstHint();
        assertEquals(feedback1,feedback);
    }

    @Test
    @DisplayName("Test the last hint")
    void lastHintTest(){
        Round round = new Round("word");
        round.guessWord("wfrd");
        assertEquals(round.getLastHint(),List.of("w",".","r","d"));
    }
}