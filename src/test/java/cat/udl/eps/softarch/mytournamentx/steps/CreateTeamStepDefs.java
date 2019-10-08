package cat.udl.eps.softarch.mytournamentx.steps;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;
import static org.hamcrest.Matchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTeamStepDefs {


    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private StepDefs stepDefs;

    @And("^There is no registered team with name \"([^\"]*)\"$")
    public void thereIsNoRegisteredTeamWithName(String name) throws Throwable {

        Assert.assertFalse(teamRepository.existsByName(name));
    }

    @When("^I register a new team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void iRegisterANewTeamWithNameGameLevelMaxPlayers(String arg0, String arg1, String arg2, int arg3) throws Throwable {

        Team team = new Team(arg0, arg1, arg2, arg3);
            stepDefs.result = stepDefs.mockMvc.perform(
                    post("/teams")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(
                            stepDefs.mapper.writeValueAsString(team))
                    .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
    }

    @And("^It has been created a team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void itHasBeenCreatedATeamWithNameGameLevelMaxPlayers(String name, String game, String level, int maxPlayers) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{team}", name)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.game", is(game)));
    }

    @And("^I cannot create a team with name \"([^\"]*)\"$")
    public void iCannotCreateATeamWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/team/{name}",name)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Given("^There is a created team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void thereIsACreatedTeamWithNameGameLevelMaxPlayers(String name, String game, String level, int maxPlayers) throws Throwable {
            Team team = new Team(name, game, level, maxPlayers);
            teamRepository.save(team);
    }

    @And("^I cannot create a team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void iCannotCreateATeamWithNameGameLevelMaxPlayers(String name, String game, String level, int maxPlayers) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/team/{game}",game)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}
