package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EditTournamentStepDefs {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private StepDefs stepDefs;



    @And("^I register a new tournament with name \"([^\"]*)\"$")
    public void iRegisterANewTournamentWithId(String name) throws Throwable {
        Tournament tournament = new Tournament();
        stepDefs.result = stepDefs.mockMvc.perform(
                put("/tournaments/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournament.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @When("^I edit tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iEditTournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        Tournament tournament = new Tournament();
        stepDefs.result = stepDefs.mockMvc.perform(
                put("/games/{id}", name, level, game)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournament.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
