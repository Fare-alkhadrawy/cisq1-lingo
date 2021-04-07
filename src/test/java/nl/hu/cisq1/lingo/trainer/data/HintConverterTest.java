package nl.hu.cisq1.lingo.trainer.data;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HintConverterTest {

    @Test
    void convertToDatabaseColumn ( ) {
        HintConverter hintConverter = new HintConverter();
        List<String> list = List.of("w","o","r","d");
        assertEquals("w,o,r,d",hintConverter.convertToDatabaseColumn(list));
    }

    @Test
    void convertToEntityAttribute ( ) {
        HintConverter hintConverter = new HintConverter();
        List<String> list = List.of("w","o","r","d");
        assertEquals(list,hintConverter.convertToEntityAttribute("w,o,r,d"));
    }
}