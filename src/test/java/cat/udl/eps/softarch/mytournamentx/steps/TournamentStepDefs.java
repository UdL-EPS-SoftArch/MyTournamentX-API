package cat.udl.eps.softarch.mytournamentx.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class TournamentStepDefs {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private StepDefs stepDefs;

    @Given("^There is no tournament with name \"([^\"]*)\"$")
    public void thereIsNoTournamentWithName(String name) {
        Assert.assertFalse("Tournament \"" + name + "\"shouldn't exist", tournamentRepository.existsByName(name));
    }


    @Given("^There is a tournament with name \"([^\"]*)\"$")
    public void thereIsATournamentWithName(String name, Tournament.Level level, String game) {
        if(!tournamentRepository.existsByName(name)){
            Tournament tournament = new Tournament();
            tournament.setName(name);
            tournament.setLevel(level);
            tournament.setGame(game);
            tournamentRepository.save(tournament);
        }
    }
/*
    @When("^I create a new tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iCreateANewTournamentWithNameLevelAndType(String name, Tournament.Level level, String game) throws Throwable {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setLevel(level);
        tournament.setGame(game);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tournaments").contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new JSONObject(stepDefs.mapper.writeValueAsString(name))
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs
                                        .authenticate())).andDo(print());

    }*/
}
