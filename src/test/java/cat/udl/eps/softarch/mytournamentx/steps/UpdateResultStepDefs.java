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
import cat.udl.eps.softarch.mytournamentx.service.TournamentService;


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
    public Match match =  new Match();
    public MatchResult matchResult1 = new MatchResult();
    public MatchResult matchResult2 = new MatchResult();


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
        tournamentService.createTournament(tournament);
        rivals.add(team1);
        rivals.add(team2);
        round.setNextRound(null);
        round.setTournament(tournament);
        round.setNumTeams(2);
        round.setRivals(rivals);
        match.setRound(round);
        matchResult1.setMatch(match);
        matchResult2.setMatch(match);
        matchResult2.setSender(team2);
        matchResult1.setSender(team1);
        matchResultRepository.save(matchResult1);
        matchResultRepository.save(matchResult2);
    }

    @Given("^Less of half plus one of the matchresults$")
    public void lessOfHalfPlusOneOfTheMatchresults() {
    }
}
