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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteTournamentStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @When("^I delete a tournament with name \"([^\"]*)\", level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iDeleteATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/tournaments/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has not been deleted a tournament with name \"([^\"]*)\"$")
    public void itHasNotBeenDeletedAGameWithId(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tournaments/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().is2xxSuccessful());
    }

    @And("^It does not exist a tournament with name \"([^\"]*)\"$")
    public void itDoesNotExistATournamentWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tournaments/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

}
