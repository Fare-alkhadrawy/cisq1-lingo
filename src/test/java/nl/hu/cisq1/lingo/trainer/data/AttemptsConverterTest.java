package nl.hu.cisq1.lingo.trainer.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttemptsConverterTest {

    @Test
    @DisplayName("Test the attempts list convert to string")
    void convertToDatabaseColumn ( ) {
        AttemptsConverter attemptsConverter = new AttemptsConverter();
        List<String> list = List.of("one","two","three");
        assertEquals("one,two,three",attemptsConverter.convertToDatabaseColumn(list));
    }

    @Test
    @DisplayName("Test the attempts string convert to List")
    void convertToEntityAttribute ( ) {
        AttemptsConverter attemptsConverter = new AttemptsConverter();
        List<String> list = List.of("one","two","three");
        assertEquals(list,attemptsConverter.convertToEntityAttribute("one,two,three"));
    }
}