package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.exception.ForbiddenException;
import cat.udl.eps.softarch.mytournamentx.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@RepositoryEventHandler
public class UpdateMatchHandler {
    private final Logger logger = LoggerFactory.getLogger(Match.class);
    protected ResultActions result;

    @Autowired
    MatchRepository matchRepository;

    @HandleAfterSave
    public void  handleMatchPreUpdate(Match match)  {
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
            roundReposotiry
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

