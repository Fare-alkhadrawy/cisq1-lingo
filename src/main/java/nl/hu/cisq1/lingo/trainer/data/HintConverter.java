package nl.hu.cisq1.lingo.trainer.data;


import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HintConverter implements AttributeConverter<List<String>, String>{

    @Override
    public String convertToDatabaseColumn (List<String> strings) {
        return strings.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<String> convertToEntityAttribute (String s) {
        return Arrays.stream(s.split(","))
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
}
