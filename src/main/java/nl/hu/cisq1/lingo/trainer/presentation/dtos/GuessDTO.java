package nl.hu.cisq1.lingo.trainer.presentation.dtos;

import javax.validation.constraints.NotBlank;

public class GuessDTO {
    @NotBlank
    public long id;
    @NotBlank
    public String guess;
}
