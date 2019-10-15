package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.repository.MatchRepository;
import cat.udl.eps.softarch.mytournamentx.repository.MatchResultRepository;
import cat.udl.eps.softarch.mytournamentx.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.it.Ma;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;


public class CreateMatchResultStepDefs {

    public static String currentUser;
    public static String currentPass;
    private Match match;
    private MatchResult matchResult;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private StepDefs stepDefs;



    @Before
    public void setup() {
        currentPass = "";
        currentUser = "";
    }

    @Given("^There is a match$")
    public void thereIsAMatch() {
        match = matchRepository.save(new Match());
    }

    @Given("^There is no registered result for this Match$")
    public void thereIsNoRegisteredResultForThisMatch() {
        Assert.assertNull(match.getWinnerTeam());
    }

    @When("^I register a new result with Description$")
    public void iRegisterANewResultWithDescription() {
        matchResult = matchResultRepository.save(new MatchResult("description", match));
    }

    @And("^There is a registered result with \"([^\"]*)\" for this match$")
    public void thereIsARegisteredResultWithForThisMatch(String description){
        Assert.assertEquals(matchResultRepository.findById(matchResult.getId()).getDescription(),description);
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

    @And("^It has been deleted my last MatchResult in that Match$")
    public void itHasBeenDeletedMyLastMatchResultInThatMatch() {
    }

    @When("^I try to register a new result$")
    public void iTryToRegisterANewResult() {
    }



}
