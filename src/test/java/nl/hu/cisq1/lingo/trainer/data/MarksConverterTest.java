package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Mark;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarksConverterTest {

    @Test
    void convertToDatabaseColumn ( ) {
        MarksConverter marksConverter = new MarksConverter();
        List<Mark> list = List.of(Mark.CORRECT,Mark.ABSENT,Mark.PRESENT);
        assertEquals("CORRECT,ABSENT,PRESENT",marksConverter.convertToDatabaseColumn(list));
    }

    @Test
    void convertToEntityAttribute ( ) {
        MarksConverter marksConverter = new MarksConverter();
        List<Mark> list = List.of(Mark.CORRECT,Mark.ABSENT,Mark.PRESENT);
        assertEquals(list,marksConverter.convertToEntityAttribute("CORRECT,ABSENT,PRESENT"));
    }
}