package nl.hu.cisq1.lingo.trainer.presentation.DTOs;

import lombok.Data;
import nl.hu.cisq1.lingo.trainer.domain.Game;

import java.util.List;

@Data
public class GameDTO {
    private int score;
    private int roundNumber;
    private List<String> hints;

    public GameDTO(Game game){
        this.score = game.getScore();
        this.roundNumber = game.roundNumber();
        this.hints = game.getLastRound().getLastHint();
    }
}
