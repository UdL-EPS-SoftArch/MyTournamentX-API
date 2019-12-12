package cat.udl.eps.softarch.mytournamentx.service;

import cat.udl.eps.softarch.mytournamentx.domain.*;
import cat.udl.eps.softarch.mytournamentx.exception.ForbiddenException;
import cat.udl.eps.softarch.mytournamentx.repository.MatchRepository;
import cat.udl.eps.softarch.mytournamentx.repository.MatchResultRepository;
import cat.udl.eps.softarch.mytournamentx.repository.RoundRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class MatchWinnerService {
    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchResultRepository matchResultRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    TournamentService tournamentService;

    public void handleMatchResultWinners(MatchResult matchResult) throws Exception {
        Map<Team, Integer> diccionari = new HashMap<>();
        for (MatchResult matchRes : matchResultRepository.findByMatch(matchResult.getMatch())) {
            if (diccionari.containsKey(matchRes.getWinner())) {
                diccionari.put(matchRes.getWinner(), diccionari.get(matchRes.getWinner()) + 1);
            } else {
                diccionari.put(matchRes.getWinner(), 1);
            }
        }
        if (diccionari.size() == 1) {
            matchResult.getMatch().setWinner(matchResult.getWinner());
            matchResult.getMatch().setHasWinner(true);
            matchRepository.save(matchResult.getMatch());
        } else {
            if (Collections.max(diccionari.values()) >= matchResult.getMatch().getRound().getNumTeams() / 2 + 1) {
                for (Team team : diccionari.keySet()) {
                    if (diccionari.get(team).equals(Collections.max(diccionari.values()))) {
                        matchResult.getMatch().setWinner(team);
                        matchResult.getMatch().setHasWinner(true);
                        matchRepository.save(matchResult.getMatch());
                    }

                }
            }

        }
        handleMatchWinners(matchResult.getMatch());
    }

    public void handleMatchWinners(Match match) throws Exception {
        Map<Team, Integer> diccionari = new HashMap<>();
        //Save values
        for (Match matchTotal : matchRepository.findByRound(match.getRound())) {
            if (matchTotal.getWinner() != null) {
                if (diccionari.containsKey(matchTotal.getWinner())) {
                    diccionari.put(matchTotal.getWinner(), diccionari.get(matchTotal.getWinner()) + 1);
                } else if (matchTotal.getWinner() != null) {
                    diccionari.put(matchTotal.getWinner(), 1);
                }
            }
        }
        //Decide winner when some team is half+1 voted
        if (match.getRound().getBestOf() / 2 + 1 == Collections.max(diccionari.values())) {
            if (diccionari.size() == 1) {
                match.getRound().setWinner(match.getWinner());
                roundRepository.save(match.getRound());
            } else {
                for (Team team : diccionari.keySet()
                ) {
                    if (diccionari.get(team).equals(Collections.max(diccionari.values()))) {
                        match.getRound().setWinner(team);
                        roundRepository.save(match.getRound());
                    }

                }
            }
        }
        handleRoundWinners(match.getRound());
    }

    private void handleRoundWinners(Round round) throws Exception {
        if (round.getNextRound() == null) {
            round.getTournament().setWinner(round.getWinner());
            tournamentRepository.save(round.getTournament());
            tournamentService.advanceState(round.getTournament());
        }
    }
}

