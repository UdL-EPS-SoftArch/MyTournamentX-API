package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.*;
import cat.udl.eps.softarch.mytournamentx.repository.*;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.it.Ma;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.params.shadow.com.univocity.parsers.tsv.TsvRoutines;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import cat.udl.eps.softarch.mytournamentx.service.TournamentService;


import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UpdateResultStepDefs {

    private Player leaderPlayer1 = new Player();
    private Player leaderPlayer2 = new Player();

    public Team team1 = new Team();
    public Team team2 = new Team();
    public List<Team> rivals = new ArrayList<>();



    public Tournament tournament = new Tournament();
    public Round round = new Round();
    public Match match1 =  new Match();
    //public Match match2 = new Match();
    public MatchResult matchResult1 = new MatchResult();
    public MatchResult matchResult2 = new MatchResult();
    //public MatchResult matchResult3 = new MatchResult();
    //public MatchResult matchResult4 = new MatchResult();



    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private StepDefs stepDefs;


    @Given("^There are some matchresults$")
    public void thereAreSomeMatchresults() throws Exception {
        team1.setName("team1");
        team1.setMaxPlayers(1);
        team1.setGame("el lol");
        team2.setName("team2");
        team2.setMaxPlayers(1);
        team2.setGame("el lol");

        teamRepository.save(team1);
        teamRepository.save(team2);

        rivals.add(team1);
        rivals.add(team2);

        tournament.setName("Working");
        tournament.setGame("el lol");
        tournament.setBestOf(1);
        tournament.setLevel(Tournament.Level.AMATEUR);
        tournament.setParticipants(rivals);
        tournamentService.createTournament(tournament);

        round.setNextRound(null);
        round.setTournament(tournament);
        round.setNumTeams(2);
        round.setBestOf(1);
        round.setRivals(rivals);
        roundRepository.save(round);

        match1.setRound(round);
        matchRepository.save(match1);
        matchResult1.setMatch(match1);
        matchResult1.setWinner(team1);
        //matchResult2.setMatch(match1);
        //matchResult2.setSender(team2);
        matchResult1.setSender(team1);

        //match2.setRound(round);
        //matchRepository.save(match2);
        //matchResult3.setMatch(match2);
        // matchResult3.setWinner(team1);
        //matchResult4.setMatch(match2);
        //matchResult4.setSender(team2);
        //matchResult3.setSender(team1);

        matchResultRepository.save(matchResult1);
        //matchResultRepository.save(matchResult2);
        //matchResultRepository.save(matchResult3);
        //matchResultRepository.save(matchResult4);

    }
    @Transactional
    @Given("^One match result has already been created$")
    public void oneMatchResultHasAlreadyBeenCreated() throws Throwable {

        Assert.assertEquals(matchResultRepository.findByMatchAndSender(match1,team1),matchResult1);
        //Assert.assertEquals(matchResultRepository.findByMatchAndSender(match1,team2),matchResult2);
        //Assert.assertEquals(matchResultRepository.findByMatchAndSender(match2,team1),matchResult3);
        //Assert.assertEquals(matchResultRepository.findByMatchAndSender(match2,team2),matchResult4);

    }

    @When("^I created my MatchResult as a TeamLeader and i'm the last team to submit it$")
    public void iCreatedMyMatchResultAsATeamLeaderAndIMTheLastTeamToSubmitIt() throws Throwable {
        matchResult2.setMatch(match1);
        matchResult2.setSender(team2);
        matchResult2.setWinner(team1);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult2))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^The winner of the Match is set$")
    public void theWinnerOfTheMatchIsSet() {
        Assert.assertEquals(matchResultRepository.findByMatchAndSender(match1,team2).getMatch().getWinner(), team1);
    }

    @Given("^There is a tournament with name \"([^\"]*)\", level \"([^\"]*)\", game \"([^\"]*)\" and bestof \"([^\"]*)\" UpdateResult$")
    public void thereIsATournamentWithNameLevelGameAndBestofUpdateResult(String name, Tournament.Level level, String game, String bestOf) throws Throwable {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setLevel(level);
        tournament.setGame(game);
        tournament.setBestOf(Integer.valueOf(bestOf));
    }
}
