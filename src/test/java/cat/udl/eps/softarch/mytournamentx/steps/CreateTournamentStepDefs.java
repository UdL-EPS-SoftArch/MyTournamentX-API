package cat.udl.eps.softarch.mytournamentx.steps;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class CreateTournamentStepDefs {

    public static String currentUsername;
    public static String currentPassword;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private StepDefs stepDefs;

    @Before
    public void setup() {
        // Clear authentication credentials at the start of every test.
        currentPassword = "";
        currentUsername = "";
    }



    @Given("^There is a Tournament Master with user \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void thereIsATournamentMasterWithUserAndPassword(String name, String password) throws Throwable {
        this.currentUsername = name;
        this.currentPassword = password;
    }

    @Given("^I'm not logged in$")
    public void iMNotLoggedIn() throws Throwable {
        currentUsername = currentPassword = null;
    }
    @And("^I log as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLogAsWithPassword(String name, String password) throws Throwable {
        this.currentUsername = name;
        this.currentPassword = password;
    }



    @Given("^There is no tournament with name \"([^\"]*)\"$")
    public void thereIsNoTournamentWithName(String name) {
        Assert.assertFalse("Tournament \"" + name + "\"shouldn't exist", tournamentRepository.existsByName(name));
    }


    @Given("^There is a tournament with name \"([^\"]*)\"$")
    public void thereIsATournamentWithName(String name) {
        if(!tournamentRepository.existsByName(name)){
            Tournament tournament = new Tournament();
            tournament.setName(name);
            tournamentRepository.save(tournament);
        }
    }
    @When("^I create a new tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iCreateANewTournamentWithNameLevelAndType(String name, Tournament.Level level, String game) throws Throwable {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setLevel(level);
        tournament.setGame(game);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tournaments")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(String.valueOf(new JSONObject(stepDefs.mapper.writeValueAsString(name)))) //MIRAR SI ES CORRECTE
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("^I'm not logged in as a tournament master$")
    public void iMNotLoggedInAsATournamentMaster() {
        currentUsername = null;
    }

    @And("^It has been created a tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void itHasBeenCreatedATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        if(!tournamentRepository.existsByName(name)){
            Tournament tournament = new Tournament();
            tournament.setName(name);
            tournament.setLevel(level);
            tournament.setGame(game);
            tournamentRepository.save(tournament);
        }

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tournaments")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(String.valueOf(new JSONObject(stepDefs.mapper.writeValueAsString(name)))) //MIRAR SI ES CORRECTE
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a tournament with name \"([^\"]*)\"$")
    public void itHasBeenCreatedATournamentWithName(String name) throws Throwable {
        Assert.assertTrue("Tournament \"" + name + "\"shouldn't exist", tournamentRepository.existsByName(name));
    }
}

