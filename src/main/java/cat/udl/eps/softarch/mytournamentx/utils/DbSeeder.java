package cat.udl.eps.softarch.mytournamentx.utils;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import cat.udl.eps.softarch.mytournamentx.domain.TournamentMaster;
import cat.udl.eps.softarch.mytournamentx.repository.*;
import cat.udl.eps.softarch.mytournamentx.service.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    private final String devMasterEmail = "masterDev@dev.com";

    final Logger logger = LoggerFactory.getLogger(Player.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    TournamentMasterRepository tournamentMasterRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TournamentService tournamentService;

    @Override
    public void run(String... strings) throws Exception {

        //This is not the best idea, but there is no dev context defined
        TournamentMaster devMaster = tournamentMasterRepository.findByEmail(devMasterEmail);
        if(devMaster == null) {
            this.logger.info("Initializing DB with testing data...");
            System.out.println();
            this.generateDB();
            this.logger.info("Finished setting up DB");
        } else {
            this.logger.info("The system already has data");
            this.logger.info("Skipping devDB generation step");
        }
    }

    private void generateDB() throws Exception{
        ArrayList<Player> players = this.createPlayers();
        ArrayList<Team> teams = this.createTeams(players);
        this.createTournamentMaster();
        this.createTournaments(teams);
    }

    private ArrayList<Player> createPlayers() {
        ArrayList<Player> playerList = new ArrayList<>(4);
        for(int i = 1; i <= 4; i++) {
            Player player = new Player();
            player.setUsername("devPlayer" + i);
            player.setPassword("patata1234");
            player.setEmail("gamerDev" + i + "@test.com");
            playerList.add(playerRepository.save(player));
        }
        return playerList;
    }

    private ArrayList<Team> createTeams(ArrayList<Player> players) {
        ArrayList<Team> teams = new ArrayList<>(players.size());
        for(int i = 1; i<=players.size(); i++) {
            Team team = new Team();
            team.setName("Team"+ i);
            team.setLevel("Amateur");
            team.setGame("Sample Game 1");
            team.setMaxPlayers(15);
            team.setLeader(players.get(i-1));

            teams.add(teamRepository.save(team));
        }
        return teams;
    }

    private TournamentMaster createTournamentMaster() {
        TournamentMaster tournamentMaster = new TournamentMaster();
        tournamentMaster.setEmail("masterDev@dev.com");
        tournamentMaster.setUsername("devMaster");
        tournamentMaster.setPassword("patataMaster1234");

        return tournamentMasterRepository.save(tournamentMaster);
    }

    private List<Tournament> createTournaments(ArrayList<Team> teams) throws Exception{

        List<Tournament> tournamentList = new ArrayList<>(2);

        Tournament nonInitialized = new Tournament();
        nonInitialized.setLevel(Tournament.Level.AMATEUR);
        nonInitialized.setName("Sample Tournament Non Initialized");
        nonInitialized.setGame("Sample Game 1");
        nonInitialized.setBestOf(3);
        nonInitialized.setDescription("This is a sample tournament");


        tournamentList.add(tournamentRepository.save(nonInitialized));

        Tournament toInitialize = new Tournament();
        toInitialize.setLevel(Tournament.Level.AMATEUR);
        toInitialize.setName("Sample Tournament Initialized");
        toInitialize.setGame("Sample Game 1");
        toInitialize.setBestOf(3);
        toInitialize.setDescription("This is a sample tournament");
        toInitialize.setParticipants(teams);

        tournamentService.createTournament(toInitialize);

        return tournamentList;
    }
}
