package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.TournamentInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentInvitationRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTournamentInvitationStepDefs {


    @Autowired
    private TournamentInvitationRepository tournamentInvitationRepository;

    @Autowired
    private StepDefs stepDefs;

    private String url;



    @When("^I create an invitation with message \"([^\"]*)\"$")
    public void iCreateAnInvitationWithMessage(String message) throws Throwable {
        TournamentInvitation tournamentInvitation = new TournamentInvitation(message);
        tournamentInvitationRepository.save(tournamentInvitation);
    }


    @And("^Exists an invitation with message \"([^\"]*)\"$")
    public void existsAnInvitationWithMessage(String message) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.message", is(message)));
    }

    @And("^And it doesn't exist an invitation with message \"([^\"]*)\"$")
    public void andItDoesnTExistAnInvitationWithMessage(String message) throws Throwable {
        Assert.assertEquals(0, tournamentInvitationRepository.count());
        Assert.assertEquals(0, tournamentInvitationRepository.findTournamentInvitationByMessage(message));
    }

    @When("^I create an invitation with no message$")
    public void iCreateAnInvitationWithNoMessage() throws Exception {
        JSONObject tournamentInvitation = new JSONObject();
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournamentInvitation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^And it exists \"([^\"]*)\" invitations$")
    public void andItExistsInvitations(int invitations) throws Throwable {
        Assert.assertEquals(invitations, tournamentInvitationRepository.count());
    }

    @When("^I create an invitation with a (\\d+) chars long message$")
    public void iCreateAnInvitationWithACharsLongMessage(int length) throws Exception {
        char[] charArray = new char[length];
        Arrays.fill(charArray, ' ');
        String str = new String(charArray);

        JSONObject tournamentInvitation = new JSONObject();
        tournamentInvitation.put("message", str);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournamentInvitation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
