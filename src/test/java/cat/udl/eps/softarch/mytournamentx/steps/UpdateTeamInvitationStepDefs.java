package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamInvitationRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UpdateTeamInvitationStepDefs {

    private TeamInvitation teamInvitation;
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamInvitationRepository teamInvitationRepository;
    @Autowired
    private PlayerRepository playerRepository;



    @Then("^The invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\" has been modified with the accepted status \"([^\"]*)\"$")
    public void theInvitationForTheUserForTheTeamHasBeenModifiedWithTheAcceptedStatus(String user, String team, String status) throws Throwable {
        boolean booleanStatus = Boolean.parseBoolean(status);
        TeamInvitation teamInvitation = teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamRepository.findTeamByName(team), playerRepository.findByEmail(user));
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitations/{id}", (teamInvitation==null ? 0 : teamInvitation.getId()))
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.accepted", is(booleanStatus))
                );
    }

    @When("^I modify the invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\" with the accepted status = \"([^\"]*)\"$")
    public void iModifyTheInvitationForTheUserForTheTeamWithTheAcceptedStatus(String user, String team, String status) throws Throwable {
        boolean booleanStatus = Boolean.parseBoolean(status);
        TeamInvitation teamInvitation = teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamRepository.findTeamByName(team), playerRepository.findByEmail(user));
        JSONObject team_json = new JSONObject();
        team_json.put("accepted",booleanStatus);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/teamInvitations/{id}",teamInvitation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(team_json.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @When("^I modify the invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\" with the new message \"([^\"]*)\"$")
    public void iModifyTheInvitationForTheUserForTheTeamWithTheNewMessage(String user, String team, String message) throws Throwable {
        TeamInvitation teamInvitation = teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamRepository.findTeamByName(team), playerRepository.findByEmail(user));
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitations/{id}", teamInvitation.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.message", is(teamInvitation.getMessage()))
                );
    }

    @Then("^The invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\" has been modified with the message = \"([^\"]*)\"$")
    public void theInvitationForTheUserForTheTeamHasBeenModifiedWithTheMessage(String user, String team, String message) throws Throwable {
        TeamInvitation teamInvitation = teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamRepository.findTeamByName(team), playerRepository.findByEmail(user));
        JSONObject team_json = new JSONObject();
        team_json.put("message",message);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/teamInvitations/{id}",teamInvitation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(team_json.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @When("^The invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\" has not been modified$")
    public void theInvitationForTheUserForTheTeamHasNotBeenModified(String team, String user) throws Throwable {
        TeamInvitation teamInvitation = teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamRepository.findTeamByName(team), playerRepository.findByEmail(user));
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitations/{id}", (teamInvitation==null ? 0 : teamInvitation.getId()))
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
