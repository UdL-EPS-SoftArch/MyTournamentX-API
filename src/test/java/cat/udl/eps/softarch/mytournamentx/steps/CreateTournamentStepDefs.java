package cat.udl.eps.softarch.mytournamentx.steps;


import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
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

    @Given("^There is a tournament with name \"([^\"]*)\", level \"([^\"]*)\", game \"([^\"]*)\" and bestof \"([^\"]*)\"$")
    public void thereIsATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game, String bestOf) throws Throwable {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setLevel(level);
        tournament.setGame(game);
        tournament.setBestOf(Integer.valueOf(bestOf));
        tournamentRepository.save(tournament);
    }


    @Given("^I create a new tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\" and bestof \"([^\"]*)\"$")
    public void iCreateANewTournamentWithNameLevelAndGameAndBestof(String name, Tournament.Level level, String game, String bestOf) throws Throwable {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setLevel(level);
        tournament.setGame(game);
        tournament.setBestOf(Integer.valueOf(bestOf));
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tournaments")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(tournament))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I create a new tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\" and bestof not specified")
    public void iCreateANewTournamentWithNameLevelAndGameAndBestof(String name, Tournament.Level level, String game) throws Throwable {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setLevel(level);
        tournament.setGame(game);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tournaments")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(tournament))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a tournament with name \"([^\"]*)\", level \"([^\"]*)\", game \"([^\"]*)\" and bestof \"([^\"]*)\"$")
    public void itHasBeenCreatedATournamentWithNameLevelGameAndBestof(String name, String level, String game, String bestOf) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tournaments/{tournament}", name)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.level", is(level)))
                .andExpect(jsonPath("$.game", is(game)))
                .andExpect(jsonPath("$.bestOf", is(Integer.parseInt(bestOf))));
    }

    @And("^It has not been created a tournament with name \"([^\"]*)\", level \"([^\"]*)\", game \"([^\"]*)\" and bestof \"([^\"]*)\"$")
    public void itHasNotBeenCreatedATournamentWithNameLevelGameAndBestof(String name, String level, String game, String bestOf) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tournamentMasters/{name}", name)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isNotFound());
    }

}
