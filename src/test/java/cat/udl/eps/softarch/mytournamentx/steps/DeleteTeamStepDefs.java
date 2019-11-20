package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteTeamStepDefs {


    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private StepDefs stepDefs;



    @When("^I delete the team called \"([^\"]*)\"$")
    public void iDeleteTheTeamCalled(String team) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/teams/{name}",team)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been deleted a team name \"([^\"]*)\"$")
    public void itHasBeenDeletedATeamName(String team) throws Throwable {
        Assert.assertFalse(teamRepository.existsByName(team));
    }

    @And("^I cannot delete team \"([^\"]*)\", because it doesn't exist$")
    public void iCannotDeleteTeamBecauseItDoesnTExist(String team) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",team)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @And("^I cannot delete team \"([^\"]*)\"$")
    public void iCannotDeleteTeam(String team) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",team)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @And("^I cannot delete team with name \"([^\"]*)\",game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+), because it doesn't have permission$")
    public void iCannotDeleteTeamWithNameGameLevelMaxPlayersBecauseTournamentMasterDoesnTHavePermission(String team, String game, String level, int maxPlayers) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}", team)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.game", is(game)))
                .andExpect(jsonPath("$.level",is(level)))
                .andExpect(jsonPath("$.maxPlayers",is(maxPlayers)))
                .andDo(print());
    }

    @And("^The team leader of team \"([^\"]*)\" is \"([^\"]*)\"$")
    public void theTeamLeaderOfTeamIs(String teamName, String leader) throws Throwable {
        Team team = teamRepository.findTeamByName(teamName);
        Player player = playerRepository.findById(leader).get();
        team.setLeader(player);
        teamRepository.save(team);
    }

    @Given("^There is a created team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+), and the team leader is \"([^\"]*)\"$")
    public void thereIsACreatedTeamWithNameGameLevelMaxPlayersAndTheTeamLeaderIs(String name, String game, String level, int maxPlayers, String teamLeader) throws Throwable {
        Team team = new Team(name, game, level, maxPlayers);
        Player player = playerRepository.findById(teamLeader).get();
        team.setLeader(player);
        teamRepository.save(team);
    }

}