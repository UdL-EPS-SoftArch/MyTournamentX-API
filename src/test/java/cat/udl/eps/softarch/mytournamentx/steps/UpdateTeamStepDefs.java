package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.JsonObject;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateTeamStepDefs {


    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private StepDefs stepDefs;

    private String newResourceUri;


    @And("^There is a created team with name \"([^\"]*)\"$")
    public void thereIsACreatedTeamWithName(String team) throws Throwable {
        Assert.assertTrue(teamRepository.existsByName(team));
    }


    @When("^I change the game of my team \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iChangeTheGameOfMyTeamTo(String team, String game) throws Throwable {
        JSONObject team_json = new JSONObject();
        team_json.put("game",game);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/teams/{name}",team)
                .contentType(MediaType.APPLICATION_JSON)
                .content(team_json.toString())
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }


    @And("^It has been changed game of team \"([^\"]*)\" to \"([^\"]*)\"$")
    public void itHasBeenChangedGameOfTeamTo(String name, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.game",is(game))).andDo(print());
    }

    @When("^I change the level of my team \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iChangeTheLevelOfMyTeamTo(String teamName, String newLevel) throws Throwable {
        JSONObject team_json = new JSONObject();
        team_json.put("level",newLevel);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/teams/{name}",teamName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(team_json.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("^It has been changed level of team \"([^\"]*)\" to \"([^\"]*)\"$")
    public void itHasBeenChangedLevelOfTeamTo(String teamName, String newLevel) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",teamName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.level",is(newLevel))).andDo(print());
    }

    @When("^I change the maxPlayers of my team \"([^\"]*)\" to (\\d+)$")
    public void iChangeTheMaxPlayersOfMyTeamTo(String teamName, int newMaxPlayers) throws Throwable {
        JSONObject team_json = new JSONObject();
        team_json.put("maxPlayers",newMaxPlayers);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/teams/{name}",teamName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(team_json.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()
        )).andDo(print());
    }

    @And("^It has been changed maxPlayers of team \"([^\"]*)\" to (\\d+)$")
    public void itHasBeenChangedMaxPlayersOfTeamTo(String teamName, int newMaxPlayers) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",teamName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.maxPlayers",is(newMaxPlayers))).andDo(print());
    }

    @And("^I cannot change maxPlayers of team \"([^\"]*)\" to (\\d+), because is an invalid number$")
    public void iCannotChangeMaxPlayersOfTeamToBecauseIsAnInvalidNumber(String teamName, int newMaxPlayers) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",teamName)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.maxPlayers",not(newMaxPlayers))).andDo(print());
    }

    @And("^I cannot change game of team \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iCannotChangeGameOfTeamToBecauseIsBlank(String teamName, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
            get("/teams/{name}",teamName)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.game",not(game))).andDo(print());
    }

    @And("^I cannot change level of team \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iCannotChangeLevelOfTeamToBecauseIsBlank(String teamName, String level) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",teamName)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level",not(level))).andDo(print());
    }
}
