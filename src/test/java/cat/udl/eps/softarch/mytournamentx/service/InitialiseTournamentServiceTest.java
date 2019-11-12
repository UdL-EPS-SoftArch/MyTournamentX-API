package cat.udl.eps.softarch.mytournamentx.service;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.Round;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import cat.udl.eps.softarch.mytournamentx.repository.MatchRepository;
import cat.udl.eps.softarch.mytournamentx.repository.RoundRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class InitialiseTournamentServiceTest {

    @Autowired
    InitialiseTournamentService initialiseTournamentService;

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    MatchRepository matchRepository;


    @BeforeEach
    void setup() {
        Tournament tournament = new Tournament();
        tournament.setName("Galactic Aquatic Football Tournament");
        tournament.setBestOf(3);
        tournament.setDescription(
                "Tournament description:" +
                "Galactic Aquatic Football 3.1.0 - Alpha testing"
        );
        tournament.setLevel(Tournament.Level.AMATEUR);
        tournament.setGame(
                "Galactic Aquatic Football 3.1.0 - Alpha testing"
        );
        tournament.setLimitDate(ZonedDateTime.now());
        tournament.setMaxParticipants(2);
        tournament.setMinParticipants(2);
        tournament.setMaxTeamPlayers(16);
        tournament.setMinTeamPlayers(16);

        String[] teamNames = {"Sharks", "Dolphins", "Octopuses", "JellyFishes"};

        List<Team> rivals = new ArrayList<>();

        for (String teamName : teamNames) {
            Team team = new Team();
            team.setName(teamName);
            team.setGame("Galactic Aquatic Football 3.1.0 - Alpha testing");
            team.setName(teamName);
            team.setLevel("AMATEUR");
            team.setMaxPlayers(16);
            rivals.add(teamRepository.save(team));
        }

        tournament.setParticipants(rivals);
        tournamentRepository.save(tournament);
    }

    @DisplayName("Create Tounament Spring @Autowired Integration")
    @Test
    @Transactional //per a que no doni errors de Lazy Inicialization
    void createTournament() throws Exception {
        Tournament tournament = tournamentRepository.findTournamentByName(
                "Galactic Aquatic Football Tournament"
        );

        initialiseTournamentService.createTournament("Galactic Aquatic Football Tournament");
        List<Round> rounds = roundRepository.findByTournament(tournament);

        assertEquals(3, rounds.size());

        Round round1 = rounds.get(0);
        assertEquals(2, round1.getRivals().size());

        Round round2 = rounds.get(1);
        assertEquals(2, round2.getRivals().size());

        Round round3 = rounds.get(2);
        assertEquals(0, round3.getRivals().size());

        List<Match> matches1 = matchRepository.findByRound(round1);
        assertEquals(tournament.getBestOf(), matches1.size());

        List<Match> matches2 = matchRepository.findByRound(round2);
        assertEquals(tournament.getBestOf(), matches2.size());

        List<Match> matches3 = matchRepository.findByRound(round3);
        assertEquals(0, matches3.size());
    }
}