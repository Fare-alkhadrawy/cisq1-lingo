package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.presentation.dtos.GuessDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class GameControllerTest {
    @Autowired
    private GameService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SpringGameRepository repository;

    private Game game;

    @BeforeEach
    void beforeEach(){
        this.game = service.startGame();
    }
    @AfterEach
    void afterEach(){
        if(repository.findById(this.game.getGameId()).isPresent()) repository.delete(game);
    }


    @DisplayName("test throw exception if the game not founded")
    @Test
    void getGameTestFail(){
        assertThrows(GameNotFoundException.class,()-> service.getGame((long) 0));
    }


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("test if the nwe game started")
    void startGame ( ) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/game");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roundNumber").value(1));    }

    @Test
    @DisplayName("Test valid guess")
    void validGuessWord ( ) throws Exception {
        GuessDTO guessDTO = new GuessDTO();
        guessDTO.guess = game.getLastRound().getWordToGuess();
        guessDTO.id = game.getGameId();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/game/guess/")
                .content(asJsonString(guessDTO))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roundStatus").value("WIN"));
    }
    @Test
    @DisplayName("Test invalid guess")
    void invalidGuessWord ( ) throws Exception {
        GuessDTO guessDTO = new GuessDTO();
        guessDTO.guess = "aaaa";
        guessDTO.id = game.getGameId();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/game/guess/")
                .content(asJsonString(guessDTO))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Test guessing to not founded game")
    void notFoundGuessWord ( ) throws Exception {
        GuessDTO guessDTO = new GuessDTO();
        guessDTO.guess = game.getLastRound().getWordToGuess();
        guessDTO.id = 0;
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/game/guess/")
                .content(asJsonString(guessDTO))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("test if the new round started and added to rounds list")
    void startRound ( ) throws Exception {
        service.doGuess(game.getLastRound().getWordToGuess(),game.getGameId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/game/startRound/"+ game.getGameId());
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roundNumber").value(2));
    }
    @Test
    @DisplayName("test start new round while the last round active")
    void cantStartRound ( ) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/game/startRound/"+ game.getGameId());
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("test start new round while the last round active")
    void notFoundStartRound ( ) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/game/startRound/"+ game.getGameId());
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
}