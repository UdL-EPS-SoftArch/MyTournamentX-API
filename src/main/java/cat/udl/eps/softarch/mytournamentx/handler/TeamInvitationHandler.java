package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.exception.BadRequestException;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamInvitationRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@Component
@RepositoryEventHandler
public class TeamInvitationHandler {
    final Logger logger = LoggerFactory.getLogger(TeamInvitation.class);

    @Autowired
    TeamInvitationRepository teamInvitationRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    @HandleBeforeCreate
    public void handleTeamInvitationPreCreate(TeamInvitation teamInvitation) {
        logger.info("Before creating: {}", teamInvitation.toString());
        if(teamInvitation.getTeam()==null || !teamRepository.existsByName(teamInvitation.getTeam().getId())  || teamInvitation.getUser()==null || !playerRepository.existsById(teamInvitation.getUser().getId())){
            throw new BadRequestException();
        }

        if(teamInvitationRepository.findTTeamInvitationByTeamAndUser(teamInvitation.getTeam(), teamInvitation.getUser()) != null)
            throw new BadRequestException();
        Team team = teamRepository.findTeamByName(teamInvitation.getTeam().getId());

        if(team.getMaxPlayers() == team.getCurrentPlayers())
            throw new BadRequestException();
    }

    @HandleBeforeSave
    public void handleTeamInvitationPreSave(TeamInvitation teamInvitation) {
        logger.info("Before updating: {}", teamInvitation.toString());
    }

    @HandleBeforeDelete
    public void handleTeamInvitationPreDelete(TeamInvitation teamInvitation) {
        logger.info("Before deleting: {}", teamInvitation.toString());
    }

    @HandleBeforeLinkSave
    public void handleTeamInvitationPreLinkSave(TeamInvitation teamInvitation, Object o) {
        logger.info("Before linking: {} to {}", teamInvitation.toString(), o.toString());
    }

    @HandleAfterDelete
    public void handleTeamInvitationPostDelete(TeamInvitation teamInvitation) {
        logger.info("After deleting: {}", teamInvitation.toString());
    }

    @HandleAfterLinkSave
    public void handleTeamInvitationPostLinkSave(TeamInvitation teamInvitation, Object o) {
        logger.info("After linking: {} to {}", teamInvitation.toString(), o.toString());
    }

}
