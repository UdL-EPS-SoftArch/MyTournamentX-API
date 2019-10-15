package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
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
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


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

    @Given("^There is no registered matchResult for this Match$")
    public void thereIsNoRegisteredResultForThisMatch() {
        Assert.assertNull(match.getWinner());
    }

    @When("^I register a new MatchResult with Description \"([^\"]*)\"$")
    public void iRegisterANewResultWithDescription(String description) throws Throwable  {
        MatchResult matchResult = new MatchResult(description,match);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchresult")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("^There is a registered MatchResult with \"([^\"]*)\" for this match$")
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
