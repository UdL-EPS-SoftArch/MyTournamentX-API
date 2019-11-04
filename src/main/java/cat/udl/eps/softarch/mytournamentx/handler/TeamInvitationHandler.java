package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.repository.TeamInvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RepositoryEventHandler
public class TeamInvitationHandler {
    final Logger logger = LoggerFactory.getLogger(TeamInvitation.class);

    @Autowired
    TeamInvitationRepository teamInvitationRepository;

    @HandleBeforeCreate
    public void handleTeamInvitationPreCreate(TeamInvitation teamInvitation) {
        logger.info("Before creating: {}", teamInvitation.toString());
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
