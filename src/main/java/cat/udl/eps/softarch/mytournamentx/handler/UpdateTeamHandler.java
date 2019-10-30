package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
@RepositoryEventHandler
public class UpdateTeamHandler {
    final Logger logger = LoggerFactory.getLogger(Team.class);
    protected ResultActions result;
    @HandleBeforeSave
    public void  handleTeamPreDelete(Team team) throws Throwable {
        logger.info("Before Updating: {}", team.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        Player player = ((Player)authentication.getPrincipal());



        if (!player.getId().equals(team.getLeader().getId())){
            throw new ForbiddenException();
        }
    }
}
