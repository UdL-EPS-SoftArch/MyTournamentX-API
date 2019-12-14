package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RepositoryEventHandler
public class CreateTeamHandler {
    final Logger logger = LoggerFactory.getLogger(Team.class);

    @HandleBeforeCreate
    public void  handleTeamPreCreate(Team team){
        logger.info("Before creating: {}", team.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        team.setLeader((Player)authentication.getPrincipal());
        team.setPlayer((Player)authentication.getPrincipal());
    }
}
