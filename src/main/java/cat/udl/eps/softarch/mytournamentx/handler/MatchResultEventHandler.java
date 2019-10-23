package cat.udl.eps.softarch.mytournamentx.handler;
import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.repository.MatchResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterLinkSave;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RepositoryEventHandler
public class MatchResultEventHandler {

    final Logger logger = LoggerFactory.getLogger(MatchResult.class);

    @Autowired
    MatchResultRepository matchResultRepository;

    @HandleAfterCreate
    public void handleDefWinner(MatchResult matchResult){
       /*Map<String, Integer> diccionari = new HashMap<>();

        for(MatchResult result:matchResultRepository.findByMatchContaining(MatchResult.match)){

            if (diccionari.containsKey(result.getWinner())){
                diccionari.replace(result.getWinner(),diccionari.get(result.getWinner()) + 1);
            }
            diccionari.put(result.getWinner(),1);
        }
        if(Collections.max(diccionari.keySet()) > match.total_teams/2){
            match.
        };*/
    }
    @HandleBeforeCreate
    public void handlePlayerPreCreate(MatchResult matchResult) {
        logger.info("Before creating: {}", matchResult.toString());
    }

    @HandleBeforeSave
    public void handlePlayerPreSave(MatchResult matchResult) {
        logger.info("Before updating: {}", matchResult.toString());
    }

    @HandleBeforeDelete
    public void handlePlayerPreDelete(MatchResult matchResult) {
        logger.info("Before deleting: {}", matchResult.toString());
    }

    @HandleBeforeLinkSave
    public void handlePlayerPreLinkSave(MatchResult matchResult, Object o) {
        logger.info("Before linking: {} to {}", matchResult.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handlePlayerPostCreate(MatchResult matchResult) {
        logger.info("After creating: {}", matchResult.toString());
        matchResultRepository.save(matchResult);
    }

    @HandleAfterDelete
    public void handlePlayerPostDelete(MatchResult matchResult) {
        logger.info("After deleting: {}", matchResult.toString());
    }

    @HandleAfterLinkSave
    public void handlePlayerPostLinkSave(MatchResult matchResult, Object o) {
        logger.info("After linking: {} to {}", matchResult.toString(), o.toString());
    }
}
