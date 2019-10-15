package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.TournamentInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentInvitationRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateTournamentInvitationStepDefs {


    @Autowired
    private TournamentInvitationRepository tournamentInvitationRepository;

    @Autowired
    private StepDefs stepDefs;


    @When("^I create an invitation with message \"([^\"]*)\"$")
    public void iCreateAnInvitationWithMessage(String message) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @And("^Exists an invitation with message \"([^\"]*)\"$")
    public void existsAnInvitationWithMessage(String message) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^And it doesn't exist an invitation with message \"([^\"]*)\"$")
    public void andItDoesnTExistAnInvitationWithMessage(String message) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I create an invitation with no message$")
    public void iCreateAnInvitationWithNoMessage() {

    }

    @And("^And it exists \"([^\"]*)\" invitations$")
    public void andItExistsInvitations(int num) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I create an invitation with a (\\d+) chars long message$")
    public void iCreateAnInvitationWithACharsLongMessage(int length) {
    }
}
