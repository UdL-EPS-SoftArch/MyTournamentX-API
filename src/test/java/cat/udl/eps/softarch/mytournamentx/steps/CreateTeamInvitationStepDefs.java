package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateTeamInvitationStepDefs {
    @Autowired
    private UserRepository userRepository;

    @Given("^The userId \"([^\"]*)\" is correct$")
    public void theUserIdIsCorrect(String userId) throws Throwable {
        Assert.assertTrue("User \"" +  userId + "\"must exist", userRepository.existsById(userId));
    }

    @And("^The teamId \"([^\"]*)\" is correct$")
    public void theTeamIdIsCorrect(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The user \"([^\"]*)\" is not in the team \"([^\"]*)\"$")
    public void theUserIsNotInTheTeam(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I create the invitation for the user \"([^\"]*)\" to participate in team \"([^\"]*)\"$")
    public void iCreateTheInvitationForTheUserToParticipateInTeam(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The sever response code is (\\d+)$")
    public void theSeverResponseCodeIs(int arg0) {
    }

    @And("^The invitation has been created for the user \"([^\"]*)\" for the team \"([^\"]*)\"$")
    public void theInvitationHasBeenCreatedForTheUserForTheTeam(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
