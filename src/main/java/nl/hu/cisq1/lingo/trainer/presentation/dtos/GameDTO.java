package nl.hu.cisq1.lingo.trainer.presentation.dtos;

import lombok.Data;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.RoundStatus;

import java.util.List;

@Data
public class GameDTO {
    private int score;
    private int roundNumber;
    private RoundStatus roundStatus;
    private List<String> hints;

    public GameDTO(Game game){

        this.score = game.getScore();
        this.roundNumber = game.roundNumber();
        this.roundStatus = game.getLastRound().getRoundStatus();
        this.hints = game.getLastRound().getLastHint();
    }
}
