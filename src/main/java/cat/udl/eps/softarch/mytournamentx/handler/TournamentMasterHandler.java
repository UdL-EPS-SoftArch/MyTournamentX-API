package cat.udl.eps.softarch.mytournamentx.handler;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.TournamentMaster;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentMasterRepository;
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

@Component
@RepositoryEventHandler
public class TournamentMasterHandler {

    final Logger logger = LoggerFactory.getLogger(TournamentMaster.class);

    @Autowired
    TournamentMasterRepository tournamentMasterRepository;

    @HandleBeforeCreate
    public void handleTournamentMasterPreCreate(TournamentMaster master) {
        logger.info("Before creating: {}", master.toString());
    }

    @HandleBeforeSave
    public void handleTournamentMasterPreSave(TournamentMaster master) {
        logger.info("Before updating: {}", master.toString());
    }

    @HandleBeforeDelete
    public void handleTournamentMasterPreDelete(TournamentMaster master) {
        logger.info("Before deleting: {}", master.toString());
    }

    @HandleBeforeLinkSave
    public void handleTournamentMasterPreLinkSave(TournamentMaster master, Object o) {
        logger.info("Before linking: {} to {}", master.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleTournamentMasterPostCreate(TournamentMaster master) {
        logger.info("After creating: {}", master.toString());
        master.encodePassword();
        tournamentMasterRepository.save(master);
    }

    @HandleAfterSave
    public void handleTournamentMasterPostSave(TournamentMaster master) {
        logger.info("After updating: {}", master.toString());
        if (master.isPasswordReset()) {
            master.encodePassword();
        }
        tournamentMasterRepository.save(master);
    }

    @HandleAfterDelete
    public void handleTournamentMasterPostDelete(TournamentMaster master) {
        logger.info("After deleting: {}", master.toString());
    }

    @HandleAfterLinkSave
    public void handleTournamentMasterPostLinkSave(TournamentMaster master, Object o) {
        logger.info("After linking: {} to {}", master.toString(), o.toString());
    }
}
