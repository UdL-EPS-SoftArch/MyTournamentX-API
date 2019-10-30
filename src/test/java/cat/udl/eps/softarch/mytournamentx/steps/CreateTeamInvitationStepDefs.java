package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.TeamInvitationRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cat.udl.eps.softarch.mytournamentx.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import cat.udl.eps.softarch.mytournamentx.domain.*;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTeamInvitationStepDefs {
    TeamInvitation teamInvitation;
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamInvitationRepository teamInvitationRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Given("^The userId \"([^\"]*)\" is correct$")
    public void theUserIdIsCorrect(String userId) throws Throwable {
        Assert.assertTrue("User with ID: " +  userId + ", must exist", userRepository.existsById(userId));
    }

    @And("^The teamId \"([^\"]*)\" is correct$")
    public void theTeamIdIsCorrect(String teamId) throws Throwable {
        Assert.assertTrue("Team with ID:  " + teamId + ", must exist", teamRepository.existsById(teamId));
    }

    @And("^The user \"([^\"]*)\" is not in the team \"([^\"]*)\"$")
    public void theUserIsNotInTheTeam(String userId, String teamId) throws Throwable {
        Team team = teamRepository.getById(teamId);
        Assert.assertFalse("User with ID: " +  userId + ", musn't be allredy in the team " +
                "with id: " + teamId, team.userInTeam(userId));
    }

    @When("^I create the invitation for the user \"([^\"]*)\" to participate in team \"([^\"]*)\"$")
    public void iCreateTheInvitationForTheUserToParticipateInTeam(String userId, String teamId) throws Throwable {
        teamInvitation = new TeamInvitation(teamId, userId, "Welcome");
            stepDefs.result = stepDefs.mockMvc.perform(
                post("/teamInvitation")
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
        TeamInvitationId teamInvitationId = new TeamInvitationId(userId,teamId);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitation/{teamInvitationId}", teamInvitationId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.userId", is(userId)))
                .andExpect(jsonPath("$.teamId", is(teamId))
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
        TeamInvitationId teamInvitationId = new TeamInvitationId(userId,teamId);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitation/{teamInvitationId}", teamInvitationId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.userId", not(userId)))
                .andExpect(jsonPath("$.teamId",not(teamId)));
    }

    @And("^The teamId \"([^\"]*)\" is not correct$")
    public void theTeamIdIsNotCorrect(String teamId) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teams/{teamId}",teamId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}
