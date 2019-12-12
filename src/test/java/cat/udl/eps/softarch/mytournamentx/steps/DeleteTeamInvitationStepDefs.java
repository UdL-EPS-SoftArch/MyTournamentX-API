package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamInvitationRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class DeleteTeamInvitationStepDefs {
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamInvitationRepository teamInvitationRepository;
    @Autowired
    private PlayerRepository playerRepository;
    
    @When("^I delete the invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\"$")
    public void iDeleteTheInvitationForTheUserForTheTeam(String user, String team) throws Throwable {
        TeamInvitation teamInvitation = teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamRepository.findTeamByName(team), playerRepository.findByEmail(user));
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/teamInvitations/{id}", teamInvitation.getId())
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("^The invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\" has been deleted$")
    public void theInvitationForTheUserForTheTeamHasBeenDeleted(String user, String team) throws Throwable {
        TeamInvitation teamInvitation = teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamRepository.findTeamByName(team), playerRepository.findByEmail(user));
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitations/{id}", (teamInvitation==null ? 0 : teamInvitation.getId()))
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("^The invitation for the user \"([^\"]*)\" for the team \"([^\"]*)\" has not been deleted$")
    public void theInvitationForTheUserForTheTeamHasNotBeenDeleted(String user, String team) throws Throwable {
        TeamInvitation teamInvitation = teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamRepository.findTeamByName(team), playerRepository.findByEmail(user));
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/teamInvitations/{id}", (teamInvitation==null ? 0 : teamInvitation.getId()))
                        .accept(MediaType.APPLICATION_JSON_UTF8).with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
