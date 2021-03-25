package nl.hu.cisq1.lingo.trainer.presentation.DTOs;

import nl.hu.cisq1.lingo.trainer.domain.Game;

import java.util.List;

public class GameDTO {
    private int score;
    private int roundNumber;
    private List<String> hints;

    public GameDTO(Game game){
        this.score = game.getScore();
        this.roundNumber = game.roundNumber();
        this.hints = game.getLastRound().getLastHint();
    }

    public int getScore ( ) {
        return score;
    }

    public int getRoundNumber ( ) {
        return roundNumber;
    }

    public List<String> getHints ( ) {
        return hints;
    }
}
