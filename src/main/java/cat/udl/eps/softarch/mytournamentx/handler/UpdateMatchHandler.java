package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.service.MatchWinnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class UpdateMatchHandler {
    private final Logger logger = LoggerFactory.getLogger(Match.class);

    @Autowired
    MatchWinnerService matchWinnerService;

    @HandleAfterCreate
    public void handleMatchPreUpdate(Match match) throws Exception {

    }
}

