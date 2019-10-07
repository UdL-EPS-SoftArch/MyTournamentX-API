package cat.udl.eps.softarch.mytournamentx.steps;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.JsonObject;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTeamStepDefs {


    @Autowired private TeamRepository teamRepository;
    @Autowired private StepDefs stepDefs;

    @And("^There is no registered team with name \"([^\"]*)\"$")
    public void thereIsNoRegisteredTeamWithName(String arg0) throws Throwable {

        Assert.assertFalse(teamRepository.existsByName(arg0));
    }

    @When("^I register a new team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void iRegisterANewTeamWithNameGameLevelMaxPlayers(String arg0, String arg1, String arg2, int arg3) throws Throwable {

            Team team = new Team(arg0,arg1,arg2,arg3);

            stepDefs.result = stepDefs.mockMvc.perform(
                    post("/teams")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(
                            stepDefs.mapper.writeValueAsString(team))
                    .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate())).andDo(print());

    }

    @And("^It has been created a team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void itHasBeenCreatedATeamWithNameGameLevelMaxPlayers(String name, String game, String level, int maxPlayers) throws Throwable {
        Team team = teamRepository.findTeamByName(name);
        Assert.assertEquals(team.getName(),name);
        Assert.assertEquals(team.getGame(),game);
        Assert.assertEquals(team.getLevel(),level);
        Assert.assertEquals(team.getMaxPlayers(),maxPlayers);
    }
}
