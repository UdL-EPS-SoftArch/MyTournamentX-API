package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.TournamentInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
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
    private PlayerRepository playerRepository;

    @Autowired
    private StepDefs stepDefs;

    private String url;



    @And("^There is a registered player with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void thereIsARegisteredPlayerWithUsernameAndPassword(String username, String password) throws Throwable {
        if (!playerRepository.existsById(username)) {
            Player player = new Player();
            player.setUsername(username);
            player.setPassword(password);
            player.encodePassword();
            playerRepository.save(player);
        }
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

    @When("^I create an invitation with no message$")
    public void iCreateAnInvitationWithNoMessage() throws Exception {
        JSONObject tournamentInvitation = new JSONObject();
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tournamentInvitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournamentInvitation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It exists \"([^\"]*)\" invitations$")
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
                post("/tournamentInvitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournamentInvitation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @When("^I create an invitation with message \"([^\"]*)\"$")
    public void iCreateAnInvitationWithMessage(String message) throws Throwable {
        JSONObject invitation = new JSONObject();
        invitation.put("message", message);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tournamentInvitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invitation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
