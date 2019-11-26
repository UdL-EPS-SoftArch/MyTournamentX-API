package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.ast.Step;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.print.attribute.standard.Media;

import java.util.Optional;

import static cat.udl.eps.softarch.mytournamentx.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void iLeaveTheTeamWithNameGameLevelMaxPlayersAndTheTeamLeaderIs(String name, String game, String level, int maxPlayers, String demoP) throws Throwable {
            stepDefs.result = stepDefs.mockMvc.perform(
                    delete("/teams/{name}", name)
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
                patch("teams/{name}/participants", teamName)
                        .contentType("text/uri-list")
                        .content(
                                player1.getUri())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
