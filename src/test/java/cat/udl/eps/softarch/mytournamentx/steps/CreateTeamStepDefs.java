package cat.udl.eps.softarch.mytournamentx.steps;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
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
    public void iRegisterANewTeamWithNameGameLevelMaxPlayers(String name, String game, String level, int maxPlayers) throws Throwable {

        Team team = new Team(name, game, level, maxPlayers);
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
                get("/teams/{name}", name)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.game", is(game)))
                .andExpect(jsonPath("$.level", is(level)))
                .andExpect(jsonPath("$.maxPlayers", is(maxPlayers))
                );
    }

    @And("^I cannot create a team with name \"([^\"]*)\"$")
    public void iCannotCreateATeamWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",name)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Given("^There is a created team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void thereIsACreatedTeamWithNameGameLevelMaxPlayers(String name, String game, String level, int maxPlayers) throws Throwable {
            Team team = new Team(name, game, level, maxPlayers);
            teamRepository.save(team);
    }


    @And("^I am the leader of the team with name \"([^\"]*)\" and my username is \"([^\"]*)\"$")
    public void iAmTheLeaderOfTheTeamWithNameAndMyUsernameIs(String team, String teamLeader) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{team}/leader", team)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
                .andExpect(jsonPath("$.id", is(teamLeader)));
    }

    @And("^I cannot create a team with name \"([^\"]*)\",game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+), because is already created$")
    public void iCannotCreateATeamWithNameGameLevelMaxPlayersBecauseIsAlreadyCreated(String name, String game, String level, int maxPlayers) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}", name)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.game", not(game)))
                .andExpect(jsonPath("$.level",not(level)))
                .andExpect(jsonPath("$.maxPlayers",not(maxPlayers)));
    }

    @And("^I cannot create a team with blank name$")
    public void iCannotCreateATeamWithBlankName() {
        // Now is 4 as the DB has data inside
        Assert.assertEquals(4,teamRepository.findAll().size());
    }

    @And("^There is a registered team with name \"([^\"]*)\"$")
    public void thereIsARegisteredTeamWithName(String name) throws Throwable {
        Assert.assertTrue(teamRepository.existsByName(name));
    }
}
