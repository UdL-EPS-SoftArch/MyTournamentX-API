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

public class CreateTeamInvitationStepDefs {
    TeamInvitation teamInvitation;

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
    }

    /*@Then("^The sever response code is (\\d+)$")
    public void theSeverResponseCodeIs(int arg0) {

    }*/

    @And("^The invitation has been created for the user \"([^\"]*)\" for the team \"([^\"]*)\"$")
    public void theInvitationHasBeenCreatedForTheUserForTheTeam(String userId, String teamId) throws Throwable {
        Assert.assertTrue("User with ID: " +  userId + ", must " +
                "be invited to team with ID: " + teamId, teamInvitationRepository.existsById(teamInvitation.getId()));
    }

    @Given("^The userId \"([^\"]*)\" is not correct$")
    public void theUserIdIsNotCorrect(String userId) throws Throwable {
        Assert.assertFalse("User with ID: " +  userId + ", musn't exist", userRepository.existsById(userId));
        throw new PendingException();
    }

    @And("^I cannot create a invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\"$")
    public void iCannotCreateAInvitationForTheUserForTheTeam(String userId, String teamId) throws Throwable {
        Assert.assertFalse("User with ID: " +  userId + ", musn't " +
                "be invited to team with ID: " + teamId, teamInvitationRepository.existsById(teamInvitation.getId()));
    }
}
