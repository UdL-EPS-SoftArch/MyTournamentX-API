package cat.udl.eps.softarch.mytournamentx.steps;

import static cat.udl.eps.softarch.mytournamentx.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;

public class JoinToTeamStepDefs {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;


    private Team team;
    private Team team2;

    @Autowired
    private StepDefs stepDefs;

    private TeamInvitation teamInv;

    @When("^I try to join a not created team with name \"([^\"]*)\"$")
    public void iTryToJoinTeamWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",name)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @And("^I cannot join the team with name \"([^\"]*)\"$")
    public void iCannotJoinTheTeamWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}",name)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @And("^I can join the team with name \"([^\"]*)\"$")
    public void iCanJoinTheTeamWithName(String name) throws Throwable {
        team = teamRepository.findTeamByName(name);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("teams/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(authenticate()))
                .andDo(print());
    }



    @And("^I already joined a team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+), and the team leader is \"([^\"]*)\"$")
    public void iAlreadyJoinedATeamWithNameGameLevelMaxPlayersAndTheTeamLeaderIs(String name, String game, String level, int maxPlayers, String demoP) throws Throwable {
        team = new Team();
        String message = stepDefs.mapper.writeValueAsString(team);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/teams/{name}",name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @When("^I leave the team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+), and the team leader is \"([^\"]*)\"$")
    public void iLeaveTheTeamWithNameGameLevelMaxPlayersAndTheTeamLeaderIs(String team_name, String game, String level, int maxPlayers, String demoP) throws Throwable {
            stepDefs.result = stepDefs.mockMvc.perform(
                    delete("/teams/{teamId}/players/{playerId}", team_name)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(authenticate()));
    }

    @Then("^I successfully leave the team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+), and the team leader is \"([^\"]*)\"$")
    public void iSuccessfullyLeaveTheTeamWithNameGameLevelMaxPlayersAndTheTeamLeaderIs(String name, String game, String level, int maxPlayers, String demoP) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{name}", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @And("^There is a team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+), and the team leader is \"([^\"]*)\"$")
    public void thereIsATeamWithNameGameLevelMaxPlayersAndTheTeamLeaderIs(String name, String game, String level, int maxPlayers, String demoP) throws Throwable {
        team = new Team();
        String message = stepDefs.mapper.writeValueAsString(team);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/teams/{name}",name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @When("^I am player \"([^\"]*)\" and I want to join to the team with name \"([^\"]*)\"$")
    public void iAmPlayerAndIWantToJoinToTheTeamWithName(String playerName, String teamName) throws Throwable {
        Player player1 = playerRepository.findByUsername(playerName);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/teams/{name}/players", teamName)
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(
                                player1.getUri())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^There are (\\d+) players in team \"([^\"]*)\"$")
    public void thereAreNoPlayersInTeam(int numPlayers, String teamName) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
            get("/teams/{name}/players", teamName)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
            .andDo(print())
            .andExpect(jsonPath("$._embedded.players", hasSize(numPlayers)));
    }

    @And("^Team \"([^\"]*)\" includes player \"([^\"]*)\"$")
    public void teamIncludesPlayer(String teamName, String playerName) throws Throwable {
        stepDefs.result
            .andExpect(jsonPath("$._embedded.players.*.id", hasItem(playerName)));
    }
}
