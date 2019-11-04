package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class EditTournamentStepDefs {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private StepDefs stepDefs;

    private ZonedDateTime startDate, finishDate;



    @And("^I register a new tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iRegisterANewTournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        Tournament tournament = new Tournament(name, level, game);
        tournamentRepository.save(tournament);
    }


    @When("^I edit tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void iEditTournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        JSONObject tournament = new JSONObject();
        tournament.put("BEGINNER", level);
        tournament.put("Fornite", game);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/tournaments/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournament.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been edited a tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void itHasBeenEditedATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc
                .perform(get("/tournaments/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @And("^It has not been edited a tournament with name \"([^\"]*)\",level \"([^\"]*)\" and game \"([^\"]*)\"$")
    public void itHasNotBeenEditedATournamentWithNameLevelAndGame(String name, Tournament.Level level, String game) throws Throwable {
        stepDefs.result = stepDefs.mockMvc
                .perform(
                        get("/games/{name}", name,level,game)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I edit tournament with name \"([^\"]*)\" and new level \"([^\"]*)\"$")
    public void iEditTournamentWithNameAndNewLevel(String name, Tournament.Level level) throws Throwable {
        JSONObject tournament = new JSONObject();
        tournament.put("BEGINNER", level);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/tournaments/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournament.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I edit the tournament with name \"([^\"]*)\" and set the start date \"([^\"]*)\" at \"([^\"]*)\"$")
    public void iEditTheTournamentWithNameAndSetTheStartDateAt(String name, String start_date, String start_hour) throws Throwable {
        LocalDate localDateStart = LocalDate.parse(start_date);
        LocalTime localTime = LocalTime.parse(start_hour);

        startDate = ZonedDateTime.of(localDateStart,localTime, ZoneId.systemDefault());

        JSONObject tournament = new JSONObject();
        tournament.put("startAt",startDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/tournaments/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournament.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^The tournament with name \"([^\"]*)\" has the start date \"([^\"]*)\" at \"([^\"]*)\"$")
    public void theTournamentWithNameHasTheStartDateAt(String name, String start_date, String start_hour) throws Throwable {
        LocalDate localDateStart = LocalDate.parse(start_date);
        LocalTime localTime = LocalTime.parse(start_hour);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;


        startDate = ZonedDateTime.of(localDateStart,localTime,ZoneId.systemDefault());
        String startDate1 = startDate.format(formatter);

        stepDefs.result = stepDefs.mockMvc
                .perform(get("/tournaments/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.startAt",is(startDate1)));
    }

    @When("^I edit the tournament with name \"([^\"]*)\" and set the finish date \"([^\"]*)\" at \"([^\"]*)\"$")
    public void iEditTheTournamentWithNameAndSetTheFinishDateAt(String name, String finish_date, String finish_hour) throws Throwable {
        LocalDate localDateFinish = LocalDate.parse(finish_date);
        LocalTime localTime = LocalTime.parse(finish_hour);

        finishDate = ZonedDateTime.of(localDateFinish,localTime,ZoneId.systemDefault());

        JSONObject tournament = new JSONObject();
        tournament.put("finishedAt",finishDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/tournaments/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournament.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^The tournament with name \"([^\"]*)\" has the finish date \"([^\"]*)\" at \"([^\"]*)\"$")
    public void theGameWithNameHasTheFinishDateAt(String name, String finish_date, String finish_hour) throws Throwable {

        LocalDate localDateFinish = LocalDate.parse(finish_date);
        LocalTime localTime = LocalTime.parse(finish_hour);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        finishDate = ZonedDateTime.of(localDateFinish,localTime,ZoneId.systemDefault());

        String finishDate1 = finishDate.format(formatter);

        stepDefs.result = stepDefs.mockMvc
                .perform(get("/tournaments/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.finishedAt",is(finishDate1)));

    }
}
