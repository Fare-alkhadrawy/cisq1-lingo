package nl.hu.cisq1.lingo.trainer.presentation.DTOs;

import javax.validation.constraints.NotBlank;

public class GuessDTO {
    @NotBlank
    public long id;
    @NotBlank
    public String guess;
}
