package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.exception.InvalidAttemptException;
import nl.hu.cisq1.lingo.trainer.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    @DisplayName("word is guessed if all letters are correct")
    void isWordToGuess ( ) {
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertTrue(feedback.isWordGuessed());
    }
    @Test
    @DisplayName("word is not guessed if not all letters are correct")
    void isWordNotToGuess ( ) {
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertFalse(feedback.isWordGuessed());
    }
    @Test
    @DisplayName("word is valid when the word correct and has a same amount letters")
    void isWordValid ( ) {
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertTrue(feedback.isWordVlid());
    }
    @Test
    @DisplayName("word is not valid when the word  not correct or has a few or to many amount letters")
    void isWordNotValid ( ) {
        Feedback feedback = new Feedback("woord", List.of(Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID));
        assertFalse(feedback.isWordVlid());
    }

    @Test
    @DisplayName("feedback is Not valid when the feedback length Not like attempt length  ")
    void isFeedbackValid ( ) {
        List<Mark> list=List.of(Mark.CORRECT);
        assertThrows(InvalidFeedbackException.class,
                () -> new Feedback("woord",list));
    }
    @ParameterizedTest(name = "Test #{index} | {0} | {1} | {2}| {3}| {4}| {5} | {6} | {7}")
    @MethodSource("HintExamples")
    @DisplayName("Show all marks when the attempt is correct")
    void FeedbackHintTest(String attempt, List<Mark> marks, List<String> hint) {
        Feedback feedback = new Feedback(attempt, marks);
        assertEquals(hint, feedback.gaveHint());
    }

    static Stream<Arguments> HintExamples() {
        return Stream.of(
                Arguments.of("woord", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT),
                        List.of("w",".",".",".","d")),
                Arguments.of("woord", List.of(Mark.ABSENT,Mark.CORRECT,Mark.PRESENT,Mark.CORRECT,Mark.ABSENT),
                        List.of(".","o","(o)","r",".")),
                Arguments.of("woord", List.of(Mark.CORRECT,Mark.PRESENT,Mark.CORRECT,Mark.PRESENT,Mark.CORRECT),
                        List.of("w","(o)","o","(r)","d")),
                Arguments.of("woord", List.of(Mark.PRESENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.PRESENT),
                        List.of("(w)",".",".",".","(d)")),
                Arguments.of("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT),
                        List.of("w","o","o","r","d")),
                Arguments.of("woord", List.of(Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT),
                        List.of(".",".",".",".",".")),
                Arguments.of("woord", List.of(Mark.PRESENT,Mark.PRESENT,Mark.PRESENT,Mark.PRESENT,Mark.PRESENT),
                        List.of("(w)","(o)","(o)","(r)","(d)")),
                Arguments.of("woord", List.of(Mark.PRESENT,Mark.PRESENT,Mark.PRESENT,Mark.PRESENT,Mark.ABSENT),
                        List.of("(w)","(o)","(o)","(r)","."))
        );
    }
    @ParameterizedTest(name = "Test #{index} | {0} | {1} | {2}| {3}| {4}")
    @MethodSource("provideHintExamples")
    @DisplayName("Show all marks when the attempt is correct")
    void provideFeedbackHintTest(String attempt, List<Mark> marks, List<String> oldHint, List<String> newHint) {
        Feedback feedback = new Feedback(attempt, marks);
        assertEquals(newHint, feedback.gaveHint(oldHint));
    }

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("woord", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT),
                        List.of("w",".",".",".","d"), List.of("w",".",".",".","d")),
                Arguments.of("woord", List.of(Mark.ABSENT,Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT),
                        List.of(".","o","(o)","r","d"),List.of(".","o",".","r","d")),
                Arguments.of("woord", List.of(Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT),
                        List.of("w","(o)",".","(r)","d"),List.of("w",".",".",".","d")),
                Arguments.of("woord", List.of(Mark.PRESENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.PRESENT),
                        List.of(".",".",".",".","."),List.of("(w)",".",".",".","(d)")),
                Arguments.of("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT),
                        List.of(".","o","(w)",".","(r)"),List.of("w","o","o","r","d"))
        );
    }



    @Test
    @DisplayName("hint is invalid when the feedback length Not like attempt length  ")
    void InvalidHitTest (){
        Feedback feedback = new Feedback("woord", List.of(Mark.INVALID,Mark.INVALID, Mark.INVALID,Mark.INVALID,Mark.INVALID));
        assertThrows(    InvalidAttemptException.class,
                feedback::gaveHint);
    }
    @Test
    @DisplayName("hint is invalid when the feedback length Not like attempt length  ")
    void InvalidHitTest2 (){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT,Mark.PRESENT, Mark.INVALID,Mark.CORRECT,Mark.ABSENT));
        List<String> list = List.of(".","o","(w)",".","(r)");
        assertThrows(    InvalidAttemptException.class,
                ()-> feedback.gaveHint(list));
    }


    @ParameterizedTest(name = "Test #{index} | {0} | {1} | {2}| {3}| {4}")
    @MethodSource("listMarksTesten")
    @DisplayName("When attempt and word to guess have a same length make for every letter feedback(correct, absent or present) else invalid ")
    void feedbackGeneratorTest(String attempt , String wordToGuess, List<Mark> marks){
        assertEquals(marks, Feedback.feedbackGenerator(attempt, wordToGuess));}
        static Stream<Arguments> listMarksTesten() {
            return Stream.of(
                    Arguments.of("word", "woord" ,List.of(Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID)),
                    Arguments.of("wonen","woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT)),
                    Arguments.of("woord","woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT)),
                    Arguments.of("worde","woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.PRESENT,Mark.PRESENT,Mark.ABSENT)),
                    Arguments.of("duren","woord", List.of(Mark.PRESENT,Mark.ABSENT,Mark.PRESENT,Mark.ABSENT,Mark.ABSENT))
            );

    }

    @Test
    @DisplayName("equals function test")
    void equalsAndHashCode(){
        Feedback one = new Feedback("w...",List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT));
        Feedback two = new Feedback("w...",List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT));
        Feedback three = new Feedback("wo..",List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT));
        Feedback four = new Feedback("w...",List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT));
        Object object = new Object();
        assertEquals(one,one);
        assertNotEquals(object,one);
        assertNotEquals(null, one);
        assertEquals( one, two);
        assertNotEquals(one, three);
        assertNotEquals(one, four);
        assertNotEquals(three,four);
        int i = one.hashCode();
        assertEquals(i, two.hashCode());
        assertEquals(i, one.hashCode());
    }
}