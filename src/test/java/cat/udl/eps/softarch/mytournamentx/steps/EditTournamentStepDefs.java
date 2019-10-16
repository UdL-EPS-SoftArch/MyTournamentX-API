package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EditTournamentStepDefs {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private StepDefs stepDefs;



    @And("^I register a new tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iRegisterANewTournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        Tournament tournament = new Tournament(name, level, game);
        tournamentRepository.save(tournament);
    }


    @When("^I edit tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iEditTournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        JSONObject tournament = new JSONObject();
        tournament.put("BEGINNER", level);
        tournament.put("Fornite", game);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/tournaments/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournament.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been edited a tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void itHasBeenEditedATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc
                .perform(get("/tournaments/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @And("^It has not been edited a tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void itHasNotBeenEditedATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc
                .perform(
                        get("/games/{name}", name,level,game)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
