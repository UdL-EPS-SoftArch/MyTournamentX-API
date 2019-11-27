package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.*;
import cat.udl.eps.softarch.mytournamentx.exception.ForbiddenException;
import cat.udl.eps.softarch.mytournamentx.repository.RoundRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentRepository;
import cat.udl.eps.softarch.mytournamentx.service.TournamentService;
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
public class UpdateRoundHandler{

    protected ResultActions result;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    TournamentService tournamentService;

    @Autowired
    TournamentRepository tournamentRepository;

    @HandleAfterSave
    public void  handleMatchPreUpdate(Round round) throws Exception {
        if(round.getNextRound() == null){
            round.getTournament().setWinner(round.getWinner());
            tournamentService.advanceState(round.getTournament());
        }
    }

}