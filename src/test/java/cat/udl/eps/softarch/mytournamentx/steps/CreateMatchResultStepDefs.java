package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.*;
import cat.udl.eps.softarch.mytournamentx.repository.*;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.it.Ma;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class CreateMatchResultStepDefs {

    public static String currentUser;
    public static String currentPass;
    private Match match;
    private Player player;
    private Team sender;
    public Team team;
    private MatchResult matchResult;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private StepDefs stepDefs;


    @Before
    public void setup() {
        currentPass = "";
        currentUser = "";
        team = new Team();
    }

    @Given("^There is a match$")
    public void thereIsAMatch() {
        match = matchRepository.save(new Match());
    }

    @Given("^There is a matchResult$")
    public void thereIsAMatchWithMatchResult() {
        matchResult.setWinner(team);
        matchResult.setSender(sender);
        matchResult.setDescription("Amazing game");

        matchResult = matchResultRepository.save(matchResult);

    }

    @Given("^There is no registered matchResult for this Match$")
    public void thereIsNoRegisteredResultForThisMatch() {
        Assert.assertNull(match.getWinner());
    }

    @When("^I register a new MatchResult with Description \"([^\"]*)\"$")
    public void iRegisterANewResultWithDescription(String description) throws Throwable  {
        MatchResult matchResult = new MatchResult();
        matchResult.setMatch(match);
        matchResult.setDescription(description);

//        jsonObject.put("match",match.getUri());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("^There is a registered MatchResult with \"([^\"]*)\" for this match$")
    public void thereIsARegisteredResultWithForThisMatch(String description){
        Assert.assertNotNull(matchResultRepository.findByDescriptionContaining(description));
        Assert.assertNotNull(matchResultRepository.findByMatch(match));
    }


    @And("^It has been created a MatchResult with Winner \"([^\"]*)\" and Description \"([^\"]*)\"$")
    public void itHasBeenCreatedAMatchResultWithWinnerAndDescription(String winner, String description) throws Throwable {
        Assert.assertNotNull(matchResultRepository.findByDescriptionContaining(description));
        Assert.assertNotNull(matchResultRepository.findByWinner(team));
        Assert.assertNotNull(matchResultRepository.findByMatch(match));
        //throw new PendingException();
    }

    @And("^There is a team$")
    public void thereIsATeam() {
        team = new Team();
        team.setName("team");
        teamRepository.save(team);
    }

    @And("^There is a player$")
    public void thereIsAPlayer() {
        player = new Player();
        player.setUsername("player");
        player.setEmail("mytourment@udl.cat");
        player.setPassword("mytourment");
        player = playerRepository.save(player);
    }

    @When("^I register a new MatchResult with Winner \"([^\"]*)\" and Description \"([^\"]*)\"$")
    public void iRegisterANewMatchResultWithWinnerAndDescription(String winner, String description) throws Throwable {

        MatchResult matchResult = new MatchResult();

        team.setName(winner);
        team.setLeader(player);
//      jsonObject.put("match",match.getUri());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/team")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(team))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult.setMatch(match);
        matchResult.setWinner(team);
        matchResult.setDescription(description);

//      jsonObject.put("match",match.getUri());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("^It has been deleted the last MatchResult of a Sender \"([^\"]*)\" in that Match$")
    public void itHasBeenDeletedMyLastMatchResultInThatMatchSender() throws Throwable {
        matchResult.setSender(sender);
        MatchResult oldMatchResult = matchResultRepository.findBySender(sender);
        matchResultRepository.delete(oldMatchResult);
    }


/*
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
    }*/
}
