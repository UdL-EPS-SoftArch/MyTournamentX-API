package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class JoinTeamtoTournamentStepDefs {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private StepDefs stepDefs;

    private ZonedDateTime startDate, finishDate;


    @When("^Team leader join his team called \"([^\"]*)\" to a tournament called \"([^\"]*)\"$")
    public void teamLeaderJoinHisTeamCalledToATournamentCalled(String team, String tournament) throws Throwable {

        Team team1 = teamRepository.findTeamByName(team);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/tournaments/{t}/participants",tournament)
                        .contentType("text/uri-list")
                        .content(
                                team1.getUri())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^There is a team called \"([^\"]*)\" in a tournament called \"([^\"]*)\"$")
    public void thereIsATeamCalledInATournamentCalled(String team, String tournament) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tournaments/{tournament}/participants/{team}", tournament, team)
                        .accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
        .andExpect(status().isOk());
    }

    @When("^A player try join team called \"([^\"]*)\" to a tournament called \"([^\"]*)\"$")
    public void aPlayerTryJoinTeamCalledToATournamentCalled(String team, String tournament) throws Throwable {
        Team team1 = teamRepository.findTeamByName(team);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/tournaments/{tournament}/participants/{team}", tournament, team)
                        .accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @And("^There isn't a team called \"([^\"]*)\" in a tournament called \"([^\"]*)\"$")
    public void thereIsnTATeamCalledInATournamentCalled(String team, String tournament) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tournaments/{tournament}/participants/{team}", tournament, team)
                        .accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
                .andExpect(status().isNotFound());
    }
}
