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
import org.springframework.test.web.servlet.ResultActions;

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

    protected ResultActions result;

    @Given("^The userId \"([^\"]*)\" is correct$")
    public void theUserIdIsCorrect(User user) throws Throwable {
        Assert.assertTrue("User with ID: " +  user.getId() + ", must exist", userRepository.existsById(user.getId()));
    }

    @And("^The teamId \"([^\"]*)\" is correct$")
    public void theTeamIdIsCorrect(Team team) throws Throwable {
        Assert.assertTrue("Team with ID:  " + team.getId() + ", must exist", teamRepository.existsById(team.getId()));
    }

    @And("^The user \"([^\"]*)\" is not in the team \"([^\"]*)\"$")
    public void theUserIsNotInTheTeam(User user, Team team) throws Throwable {

        Assert.assertFalse("User with ID: " +  user.getId() + ", musn't be allredy in the team " +
                "with id: " + team.getId(), team.userInTeam(user.getId()));
    }

    @When("^I create the invitation for the user \"([^\"]*)\" to participate in team \"([^\"]*)\"$")
    public void iCreateTheInvitationForTheUserToParticipateInTeam(User user, Team team) throws Throwable {
        teamInvitation = new TeamInvitation(team.getId(), user.getId(), "Welcome");
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
    public void theInvitationHasBeenCreatedForTheUserForTheTeam(User user, Team team) throws Throwable {
        TeamInvitationId teamInvitationId = new TeamInvitationId(user.getId(),team.getId());
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitation/{teamInvitationId}", teamInvitationId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.userId", is(user.getId())))
                .andExpect(jsonPath("$.teamId", is(team.getId()))
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

    @Then("^The sever response code is (\\d+)$")
    public void theSeverResponseCodeIs(int code) throws Throwable {
        result.andExpect(status().is(code));
    }
}
