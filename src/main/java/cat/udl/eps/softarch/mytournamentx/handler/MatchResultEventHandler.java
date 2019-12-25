package cat.udl.eps.softarch.mytournamentx.handler;
import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.repository.MatchResultRepository;
import cat.udl.eps.softarch.mytournamentx.repository.MatchRepository;
import cat.udl.eps.softarch.mytournamentx.service.MatchWinnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterLinkSave;
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

@Component
@RepositoryEventHandler
public class MatchResultEventHandler {

    final Logger logger = LoggerFactory.getLogger(MatchResult.class);

    @Autowired
    MatchResultRepository matchResultRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchWinnerService matchWinnerService;

    @HandleAfterCreate
    public void handleDefWinner(MatchResult matchResult) {
        logger.info("After updating: {}", matchResult.toString());
    }

    @HandleBeforeCreate
    public void handlePlayerPreCreate(MatchResult matchResult) throws Throwable {
        logger.info("Before create: {}", matchResult.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());
        Player player = ((Player) authentication.getPrincipal());
        if (matchResult.getSender() == null) {
            throw new BadRequestException();
        }
        if (!matchResult.getMatch().getRound().getRivals().contains(matchResult.getWinner())) {
            throw new BadRequestException();
        }
        if (!player.getUsername().equals(matchResult.getSender().getLeader().getUsername())) {
            throw new ForbiddenException();
        }
        if (matchResultRepository.findByMatchAndSender(matchResult.getMatch(), matchResult.getSender()) != null) {
            matchResultRepository.delete(matchResult);
        }
        logger.info("Before create, after check");
    }

    @HandleBeforeSave
    public void handlePlayerPreSave(MatchResult matchResult) {
        logger.info("Before updating: {}", matchResult.toString());

    }

    @HandleAfterCreate
    public void handlePlayerAfterSave(MatchResult matchResult) throws Exception {
        logger.info("After updating: {}", matchResult.toString());
        if (matchResult.getMatch().getRound().getNumTeams() == matchResultRepository.findByMatch(matchResult.getMatch()).size()) {
            matchWinnerService.handleMatchResultWinners(matchResult);
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
