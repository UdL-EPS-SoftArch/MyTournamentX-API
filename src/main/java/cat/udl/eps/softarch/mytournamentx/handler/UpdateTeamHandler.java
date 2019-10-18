package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class UpdateTeamHandler {
    final Logger logger = LoggerFactory.getLogger(Team.class);

    @HandleBeforeCreate
    public void  handleTeamPreUpdate(Team team){
        logger.info("Before updating: {}", team.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        Player log_player = (Player)authentication.getPrincipal();


    }
}
