package cat.udl.eps.softarch.mytournamentx.steps;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CreateTournamentStepDefs {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private StepDefs stepDefs;

    @Given("^There is no tournament with name \"([^\"]*)\"$")
    public void thereIsNoTournamentWithName(String name) {
        Assert.assertFalse(tournamentRepository.existsByName(name));
    }

    @When("^I create a new tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iCreateANewTournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        Tournament tournament = new Tournament(name, level, game);
            stepDefs.result = stepDefs.mockMvc.perform(
                    post("/tournaments")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(
                            stepDefs.mapper.writeValueAsString(tournament))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
    }

    @And("^It has been created a tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void itHasBeenCreatedATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tournaments/{tournament}", name)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.level", is(level.toString())))
                .andExpect(jsonPath("$.game", is(game)));
    }

    @And("^It has not been created a tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void itHasNotBeenCreatedATournamentWithNameLevelAndGame(String name,Tournament.Level level, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tournamentMasters/{name}", name)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isNotFound());
    }

    @Given("^There is a tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void thereIsATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        Tournament tournament = new Tournament(name, level, game);
        tournamentRepository.save(tournament);
    }
}