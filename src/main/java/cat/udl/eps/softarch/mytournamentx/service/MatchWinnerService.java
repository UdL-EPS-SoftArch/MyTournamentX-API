package cat.udl.eps.softarch.mytournamentx.service;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.repository.MatchRepository;
import cat.udl.eps.softarch.mytournamentx.repository.MatchResultRepository;
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

    public void handleMatchResultWinners(MatchResult matchResult ){
        Map<Team, Integer> diccionari = new HashMap<>();
        for (MatchResult matchRes:matchResultRepository.findByMatch(matchResult.getMatch())) {
            if (diccionari.containsKey(matchRes.getWinner())) {
                diccionari.replace(matchRes.getWinner(), diccionari.get(matchRes.getWinner()) + 1);
            }
            diccionari.put(matchRes.getWinner(), 1);

        }
        if(diccionari.size() == 1){
            matchResult.getMatch().setWinner(matchResult.getWinner());
            matchRepository.save(matchResult.getMatch());
        }
        else {
            if(Collections.max(diccionari.values()) > matchResult.getMatch().getRound().getNumTeams()/2 + 1){
                for (Team team:diccionari.keySet()
                ) {
                    if (diccionari.get(team).equals(Collections.max(diccionari.values()))){
                        matchResult.getMatch().setWinner(team);
                        matchRepository.save(matchResult.getMatch());
                    }

                }
            }

        }
    }
    public void handleMatchWinners(Match match) {
        int checktheWinner = 0;
        for (Match matchTotal:matchRepository.findByRound(match.getRound())){
            if(matchTotal.getWinner() != null){
                checktheWinner= checktheWinner+1;
            }
        }
        if (match.getRound().getBestOf() / 2 + 1 == checktheWinner) {
            checkWinners(match);
        }
    }


    private void checkWinners(Match match){
        Map<Team, Integer> diccionari = new HashMap<>();
        for (Match matchTotal:matchRepository.findByRound(match.getRound())) {
            if (diccionari.containsKey(matchTotal.getWinner())) {
                diccionari.replace(matchTotal.getWinner(), diccionari.get(matchTotal.getWinner()) + 1);
            }
            if(matchTotal.getWinner() != null) {
                diccionari.put(matchTotal.getWinner(), 1);
            }
        }
        if(diccionari.size() == 1){
            match.getRound().setWinner(match.getWinner());
        }
        else {
            if(Collections.max(diccionari.values()) > match.getRound().getBestOf()/2 + 1){
                for (Team team:diccionari.keySet()
                ) {
                    if (diccionari.get(team).equals(Collections.max(diccionari.values()))){
                        match.getRound().setWinner(team);
                    }

                }
            }

        }

    }

}

