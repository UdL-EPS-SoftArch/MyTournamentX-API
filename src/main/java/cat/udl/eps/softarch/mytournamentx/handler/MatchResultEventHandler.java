package cat.udl.eps.softarch.mytournamentx.handler;
import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.repository.MatchResultRepository;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import cat.udl.eps.softarch.mytournamentx.exception.BadRequestException;
import cat.udl.eps.softarch.mytournamentx.exception.ForbiddenException;
import javax.transaction.Transactional;
import java.util.List;


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
    public void handlePlayerPreCreate(MatchResult matchResult) throws Throwable {
        logger.info("Before create: {}", matchResult.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());
        Player player = ((Player)authentication.getPrincipal());
        if(matchResult.getSender() == null){ throw new BadRequestException(); }
        if(!matchResult.getMatch().getRound().getRivals().contains(matchResult.getWinner())){
            throw new BadRequestException();
        }
        if (!player.getUsername().equals(matchResult.getSender().getLeader().getUsername())){
            throw new ForbiddenException();
        }
       if(matchResultRepository.findByMatchAndSender(matchResult.getMatch(), matchResult.getSender()) != null){
           matchResultRepository.delete(matchResult);
       }
        logger.info("Before create, after check");
    }

    @HandleBeforeSave
    public void handlePlayerPreSave(MatchResult matchResult) {
        logger.info("Before updating: {}", matchResult.toString());

    }
    @HandleAfterSave
    public void handlePlayerAfterSave(MatchResult matchResult) {
        logger.info("After updating: {}", matchResult.toString());
        if(matchResult.getMatch().getRound().getNumTeams() ==
                matchResultRepository.findByMatch(matchResult.getMatch()).size()){
            checkWinners(matchResult);
        }

    }

    private void checkWinners(MatchResult matchResult){
        Map<Team, Integer> diccionari = new HashMap<>();
        for (MatchResult matchRes:matchResultRepository.findByMatch(matchResult.getMatch())) {
            if (diccionari.containsKey(matchRes.getWinner())) {
                diccionari.replace(matchRes.getWinner(), diccionari.get(matchRes.getWinner()) + 1);
            }
            diccionari.put(matchRes.getWinner(), 1);

        }
        if(diccionari.size() == 1){
            matchResult.getMatch().setWinner(matchResult.getWinner());
        }
        else {
            if(Collections.max(diccionari.values()) > matchResult.getMatch().getRound().getNumTeams()/2 + 1){
                for (Team team:diccionari.keySet()
                     ) {
                    if (diccionari.get(team).equals(Collections.max(diccionari.values()))){
                        matchResult.getMatch().setWinner(team);
                }

                }
            }

        }

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
