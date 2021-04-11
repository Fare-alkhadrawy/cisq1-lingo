package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.trainer.exception.RoundPlayingException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Game")
@Data
@NoArgsConstructor
public class Game implements Serializable{
        @Id
        @GeneratedValue
        private Long gameId;

        private int score;
        private GameStatus gameStatus;

        @OneToMany(cascade=CascadeType.ALL)
        @JoinColumn(name="gameId")
        private List<Round> rounds = new ArrayList<>();

        public Game(String word){
            Round round = new Round(word);
            this.gameStatus = GameStatus.PLAYING;
            this.rounds.add(round);
            this.score = 0;
        }


        public void startRound(String wordToGuess) {
                if (getLastRound().getRoundStatus() != RoundStatus.WIN) {
                    throw new RoundPlayingException("Round stil playing");

            }else {
                Round round = new Round(wordToGuess);
                rounds.add(round);
            }
        }

        public void guessWord(String word) {
            getLastRound().guessWord(word);
            if (getLastRound().getRoundStatus() != RoundStatus.PLAYING) {
                if (getLastRound().isRoundWon()) {
                    this.score += scoreBerekening(getLastRound());
                } else {
                    this.gameStatus = GameStatus.LOST;
                }
            }
        }

        public Round getLastRound(){
            return this.rounds.get(rounds.size()-1);
        }

        public int scoreBerekening(Round round){
           return  5 * (6 - round.getFeedbackList().size()) + 5;
        }

        public int wordLength(){
            if (getLastRound().getWordToGuess().length()< 7)
                return getLastRound().getWordToGuess().length()+1;
            else return 5;
        }



        public int roundNumber(){
            return rounds.size();
        }
}
