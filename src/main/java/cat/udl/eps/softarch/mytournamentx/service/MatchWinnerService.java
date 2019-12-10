package cat.udl.eps.softarch.mytournamentx.service;

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
    public void handleMatchWinners() {

    }
}
