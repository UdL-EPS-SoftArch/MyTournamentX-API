package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.handler.CreateTeamHandler;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamInvitationRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cat.udl.eps.softarch.mytournamentx.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import cat.udl.eps.softarch.mytournamentx.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import javax.validation.constraints.AssertTrue;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTeamInvitationStepDefs {
    private TeamInvitation teamInvitation;
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamInvitationRepository teamInvitationRepository;
    @Autowired
    private PlayerRepository playerRepository;

    protected ResultActions result;

    @Given("^The userId \"([^\"]*)\" is correct$")
    public void theUserIdIsCorrect(String userId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
        //Assert.assertTrue(userRepository.existsById(userId));
    }

    @And("^The teamId \"([^\"]*)\" is correct$")
    public void theTeamIdIsCorrect(String teamId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
        //Assert.assertTrue(teamRepository.existsById(teamId));
    }

    @And("^The user \"([^\"]*)\" is not in the team \"([^\"]*)\"$")
    public void theUserIsNotInTheTeam(String userEmail, String teamId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        Team team = teamRepository.findTeamByName(teamId);
        Player player = playerRepository.findByEmail(userEmail);
        Assert.assertFalse(team.userInTeam(player));
    }

    @When("^I create the invitation for the user \"([^\"]*)\" to participate in team \"([^\"]*)\"$")
    public void iCreateTheInvitationForTheUserToParticipateInTeam(String userId, String teamId) throws Throwable {
        TeamInvitation teamInvitation = new TeamInvitation(userId, teamId, "Welcome");

        stepDefs.result = stepDefs.mockMvc.perform(
            post("/teamInvitations")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        stepDefs.mapper.writeValueAsString(teamInvitation))
                .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
        .andDo(print());

    }

    /*@Then("^The sever response code is (\\d+)$")
    public void theSeverResponseCodeIs(int arg0) {

    }*/

    @And("^The invitation has been created for the user \"([^\"]*)\" for the team \"([^\"]*)\"$")
    public void theInvitationHasBeenCreatedForTheUserForTheTeam(String userId, String teamId) throws Throwable {
        TeamInvitation teamInvitation =new TeamInvitation(userId, teamId, "Welcome");
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitations/{id}", teamInvitation.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.message", is(teamInvitation.getMessage()))
                );
    }

    @Given("^The userId \"([^\"]*)\" is not correct$")
    public void theUserIdIsNotCorrect(String userId) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/user/{userId}",userId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @And("^I cannot create a invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\"$")
    public void iCannotCreateAInvitationForTheUserForTheTeam(String userId, String teamId) throws Throwable {
        TeamInvitation teamInvitation = new TeamInvitation(userId,teamId,"");
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitations/{id}", teamInvitation.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", not(teamInvitation.getId())));
    }

    @And("^The teamId \"([^\"]*)\" is not correct$")
    public void theTeamIdIsNotCorrect(String teamId) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{teamId}",teamId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Then("^The sever response code is (\\d+)$")
    public void theSeverResponseCodeIs(int code) throws Throwable {
        stepDefs.result.andExpect(status().is(code));
    }

    @And("^There is empty room in the team \"([^\"]*)\"$")
    public void thereIsEmptyRoomInTheTeam(String teamId) throws Throwable {
        Team team = teamRepository.findTeamByName(teamId);
        Assert.assertTrue(team.getMaxPlayers()>team.getCurrentPlayers());
    }

    @And("^The user \"([^\"]*)\" is in the team \"([^\"]*)\"$")
    public void theUserIsInTheTeam(String userEmail, String teamId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Team team = teamRepository.findTeamByName(teamId);
        Player player = playerRepository.findByEmail(userEmail);
        Assert.assertFalse(team.userInTeam(player));
    }
}
