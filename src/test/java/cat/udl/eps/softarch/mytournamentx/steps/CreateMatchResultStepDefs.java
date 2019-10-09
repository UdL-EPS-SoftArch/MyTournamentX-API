package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.repository.MatchResultRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateMatchResultStepDefs {

    Match match;
    @Autowired
    MatchResultRepository;


    @Autowired
    MatchRepository


    MatchResult matchResult = new MatchResult("","");

    @Given("^There is a Match$")
    public void thereIsMatch() {
        match= new Match();

    }


    @Given("^There is no registered result for this Match$")
    public void thereIsNoRegisteredResultForThisMatch() {
        matchResult.setMatch(match);
        Assert.assertEquals(matchResult.getWinner(),"");
    }

    @And("^I'm logged in like team's leader \"([^\"]*)\" who has played this Match\"([^\"]*)\"$")
    public void iMLoggedInLikeTeamSLeaderWhoHasPlayedThisMatch(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I register a new result with Winner \"([^\"]*)\" and Description \"([^\"]*)\"$")
    public void iRegisterANewResultWithWinnerAndDescription(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @And("^It has been created a MatchResult with Winner \"([^\"]*)\" and Description \"([^\"]*)\"$")
    public void itHasBeenCreatedAMatchResultWithWinnerAndDescription(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I try to register a new result with an invalid Winner$")
    public void iTryToRegisterANewResultWithAnInvalidWinner() {
    }

    @And("^The object is not created$")
    public void theObjectIsNotCreated() {
    }

    @Given("^There is a registered result for this Match$")
    public void thereIsARegisteredResultForThisMatch() {
    }

    @And("^It has been deleted my last MatchResult in that Match$")
    public void itHasBeenDeletedMyLastMatchResultInThatMatch() {
    }

    @When("^I try to register a new result$")
    public void iTryToRegisterANewResult() {
    }
}
