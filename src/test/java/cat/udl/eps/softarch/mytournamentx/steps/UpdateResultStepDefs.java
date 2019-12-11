
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
    Match match1 = new Match();
    Match match2 = new Match();
    Match match3 = new Match();
    MatchResult matchResult = new MatchResult();
    MatchResult matchResult1 = new MatchResult();
    MatchResult matchResult2 = new MatchResult();
    MatchResult matchResult3 = new MatchResult();
    MatchResult matchResult4 = new MatchResult();
    MatchResult matchResult5 = new MatchResult();
    MatchResult matchResult6 = new MatchResult();
    MatchResult matchResult7 = new MatchResult();
    MatchResult matchResult8 = new MatchResult();
    MatchResult matchResult9 = new MatchResult();


    Player player = new Player();

    Team team = new Team();
    Team team2 = new Team();
    Team team3 = new Team();

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



    @And("^There is a number of matchs for the round$")
    public void thereIsANumberOfMatchsForTheRound() {
        match1.setRound(round);
        matchRepository.save(match1);
        match2.setRound(round);
        matchRepository.save(match2);
        match3.setRound(round);
        matchRepository.save(match3);

    }

    @And("^There is a created different number of teams$")
    public void thereIsACreatedDifferentNumberOfTeams() {
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

        team2.setName("TEAM2");
        team2.setGame("PACYBITS");
        team2.setLevel("AMATEUR");
        Set<Player> playerList2 = new HashSet<>();
        playerList2.add(player);
        team2.setPlayers(playerList);
        team2.setMaxPlayers(1);
        team2.setLeader(player);

        teamRepository.save(team2);

        team3.setName("TEAM3");
        team3.setGame("PACYBITS");
        team3.setLevel("AMATEUR");
        Set<Player> playerList3 = new HashSet<>();
        playerList3.add(player);
        team3.setPlayers(playerList);
        team3.setMaxPlayers(1);
        team3.setLeader(player);

        teamRepository.save(team3);
    }

    @When("^I post nine MatchResults$")
    public void iPostAMatchResults() throws Exception{
        matchResult1.setMatch(match1);
        matchResult1.setSender(team);
        matchResult1.setWinner(team);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult1))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult2.setMatch(match1);
        matchResult2.setSender(team2);
        matchResult2.setWinner(team);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult2))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult3.setMatch(match1);
        matchResult3.setSender(team3);
        matchResult3.setWinner(team2);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult3))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult4.setMatch(match2);
        matchResult4.setSender(team);
        matchResult4.setWinner(team2);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult4))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult5.setMatch(match2);
        matchResult5.setSender(team2);
        matchResult5.setWinner(team2);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult5))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult6.setMatch(match2);
        matchResult6.setSender(team3);
        matchResult6.setWinner(team2);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult6))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult7.setMatch(match3);
        matchResult7.setSender(team);
        matchResult7.setWinner(team);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult7))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult8.setMatch(match3);
        matchResult8.setSender(team2);
        matchResult8.setWinner(team);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult8))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        matchResult9.setMatch(match3);
        matchResult9.setSender(team3);
        matchResult9.setWinner(team);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult9))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Transactional
    @And("^The winner of the Matchs are set$")
    public void theWinnerOfTheMatchsAreSet() {
        Assert.assertEquals(team.getName(), matchResultRepository.findByMatchAndSender(match1,matchResult1.getSender()).getMatch().getWinner().getName());
        Assert.assertEquals(team2.getName(), matchResultRepository.findByMatchAndSender(match2,matchResult4.getSender()).getMatch().getWinner().getName());
        Assert.assertEquals(team.getName(), matchResultRepository.findByMatchAndSender(match3,matchResult7.getSender()).getMatch().getWinner().getName());
    }
    @Transactional
    @And("^The winner of the Round for the Matches is set$")
    public void theWinnerOfTheRoundForTheMatchesIsSet() {
        Team Winner_test = matchResultRepository.findByMatchAndSender(match1,matchResult1.getSender()).getMatch().getRound().getWinner();
        Assert.assertEquals(team.getName(), Winner_test.getName());
    }

    @And("^The winner of the Tournament for the Round is set$")
    public void theWinnerOfTheTournamentForTheRoundIsSet() {
        Team Winner_test = matchResultRepository.findByMatchAndSender(match1,matchResult1.getSender()).getMatch().getRound().getTournament().getWinner();
        Assert.assertEquals(team.getName(), Winner_test.getName());
    }

    @And("^There is a round created for SecondTournament$")
    public void thereIsARoundCreatedForSecondTournament() {
        round.setBestOf(3);
        round.setNextRound(null);
        round.setNumTeams(3);
        List<Team> rivals = new ArrayList<>();
        rivals.add(team);
        rivals.add(team2);
        rivals.add(team3);
        round.setRivals(rivals);
        round.setTournament(tournament);
        roundRepository.save(round);
    }
}