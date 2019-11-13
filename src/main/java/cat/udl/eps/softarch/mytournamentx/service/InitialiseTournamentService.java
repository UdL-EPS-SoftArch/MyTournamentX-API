package cat.udl.eps.softarch.mytournamentx.service;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.Round;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import cat.udl.eps.softarch.mytournamentx.repository.MatchRepository;
import cat.udl.eps.softarch.mytournamentx.repository.RoundRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cat.udl.eps.softarch.mytournamentx.utils.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cat.udl.eps.softarch.mytournamentx.utils.MathUtils.intDivisonTop;


@Service
public class InitialiseTournamentService {

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    MatchRepository matchRepository;


    public void createTournament(String name) throws Exception {
        // Assume n² rivals
        //          0
        //        0   0
        //       0 0 0 0

        // Assume: 2^n | ∀ n ∈ N : n > 0

        Tournament tournament = tournamentRepository.findTournamentByName(name);

        List<Team> rivals = tournament.getParticipants();
        // Create Round
        // int roundsNum = MathUtils.factorial(rivals.size() / 2);
        int roundsNum = rivals.size() - 1;

        Collections.shuffle(rivals);
        List<Round> rounds = new ArrayList<>(roundsNum);
        for(int i = 0; i < roundsNum; i++) {
            if(i < intDivisonTop(roundsNum, 2)) {
                List<Team> roundRivals = new ArrayList<>(rivals.subList(2*i, 2*i+2));
                Round tmpRound = createRound(roundRivals, tournament);
                rounds.add(tmpRound);
                // Create Matches
                createMatches(tmpRound);
            }
            else{
                Round tmpRound = createRound(new ArrayList<>(), tournament);
                rounds.add(tmpRound);
            }
        }

        setRoundLinks(rounds, roundsNum);
    }

    private void setRoundLinks(List<Round> rounds, int roundsNum) {
        for(int i = 0; i < roundsNum - 1; i++) {
            rounds.get(i).setNextRound(rounds.get(roundsNum /2
                    + (MathUtils.intDivisonFloor(i, 2) + 1))
            );
        }
    }

    private void createMatches(Round round) {
        for(int i = 0; i < round.getBestOf(); i++) {
            String matchDescription = getMatchDescription(round.getRivals(), i);
            createMatch(matchDescription, round);
        }
    }

    private String getMatchDescription(List<Team> rivals, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Match ")
                .append(index+1)
                .append("\n");

        for(int j = 0; j < rivals.size() -1; j++){
            stringBuilder.append(rivals.get(j).getName())
                    .append(" VS ");
        }
        stringBuilder.append(rivals.get(rivals.size() - 1).getName());

        return stringBuilder.toString();
    }

    private void createMatch(String description, Round round) {
        Match match = new Match();
        match.setDescription(description);
        match.setRound(round);
        matchRepository.save(match);
    }

    private Round createRound(List<Team> rivals, Tournament tournament) {
        Round round = new Round();
        round.setBestOf(tournament.getBestOf());
        round.setRivals(rivals);
        round.setNumTeams(rivals.size());
        round.setTournament(tournament);
        roundRepository.save(round);
        return round;
    }
}
