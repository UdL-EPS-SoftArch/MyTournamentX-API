
package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.*;
import cat.udl.eps.softarch.mytournamentx.repository.*;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import cucumber.api.java.it.Ma;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.params.shadow.com.univocity.parsers.tsv.TsvRoutines;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.io.OutputStream;
import java.util.*;
import cat.udl.eps.softarch.mytournamentx.service.TournamentService;
import javax.transaction.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UpdateResultStepDefs {

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


    Tournament tournament = new Tournament();
    Round round = new Round();
    Match match = new Match();
    MatchResult matchResult = new MatchResult();

    Player player = new Player();

    Team team = new Team();
    Team team2 = new Team();

    @Given("^There is a tournament with name \"([^\"]*)\", level \"([^\"]*)\", game \"([^\"]*)\" and bestof \"([^\"]*)\" UpdateResult$")
    public void thereIsATournamentWithNameLevelGameAndBestofUpdateResult(String name, Tournament.Level level, String game, int bestOf) throws Throwable {

        tournament.setName(name);
        tournament.setLevel(level);
        tournament.setGame(game);
        tournament.setBestOf(bestOf);
        tournamentRepository.save(tournament);
    }

    @And("^There is a created team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers \"" +
            "([^\"]*)\", and the team leader is \"([^\"]*)\" UpdateResult$")
    public void thereIsACreatedTeamWithNameGameLevelMaxPlayersAndTheTeamLeaderIsUpdateResult(String name, String game, String level, int maxPlayers, String teamLeader) throws Throwable {

        player = playerRepository.findByEmail("demoP@mytournamentx.game");

        playerRepository.save(player);

        team.setName("TEAM1");
        team.setGame("PACYBITS");
        team.setLevel("AMATEUR");
        Set<Player> playerList = new HashSet<>();
        playerList.add(player);
        team.setPlayers(playerList);
        team.setMaxPlayers(1);
        team.setLeader(player);

        teamRepository.save(team);
    }

    @And("^There is a round with Round \"([^\"]*)\", bestof \"([^\"]*)\", numTeams \"([^\"]*)\", List<Team> \"([^\"]*)\", tournament \"([^\"]*)\"$")
    public void thereIsARoundWithRoundBestofNumTeamsListTeamTournament(String nextRound, int bestOf, int numTeams, String rivals31, String tournament1) throws Throwable {

        round.setBestOf(bestOf);
        round.setNextRound(null);
        round.setNumTeams(numTeams);
        List<Team> rivals = new ArrayList<>();
        rivals.add(team);
        round.setRivals(rivals);
        round.setTournament(tournament);
        roundRepository.save(round);
    }

    @And("^There is a match UpdateResult$")
    public void thereIsAMatchUpdateResult() {

        match.setRound(round);
        matchRepository.save(match);
    }

    @When("^I post a matchResult with Match \"([^\"]*)\", Team \"([^\"]*)\", Team \"([^\"]*)\" UpdateResult$")
    public void thereIsAMatchResultWithMatchTeamTeamUpdateResult(String match1, String sender2, String winner3) throws Throwable {

        matchResult.setMatch(match);
        matchResult.setSender(team);
        matchResult.setWinner(team);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^There is a registered player with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\" " +
            "UpdateResult$")
    public void thereIsARegisteredPlayerWithUsernameEmailAndPasswordUpdateResult(String username, String email,
                                                                                 String password) throws Throwable {
        if (!playerRepository.existsById(username)) {
            player.setUsername(username);
            player.setEmail(email);

            player.setPassword(password);
            player.encodePassword();
            playerRepository.save(player);
        }
    }

    @Transactional
    @And("^The winner of the Match is set$")
    public void theWinnerOfTheMatchIsSet() {
        Assert.assertEquals(team.getName(), matchResultRepository.findByMatchAndSender(match,matchResult.getSender()).getMatch().getWinner().getName());
    }

    @And("^The winner of the Round is set$")
    public void theWinnerOfTheRoundIsSet() {
        Team Winner_test = matchResultRepository.findByMatchAndSender(match,matchResult.getSender()).getMatch().getRound().getWinner();
        Assert.assertEquals(team.getName(), Winner_test.getName());
    }

    @And("^The winner of the Tournament is set$")
    public void theWinnerOfTheTournamentIsSet() {
        Team Winner_test = matchResultRepository.findByMatchAndSender(match,matchResult.getSender()).getMatch().getRound().getTournament().getWinner();
        Assert.assertEquals(team.getName(), Winner_test.getName());
    }
}