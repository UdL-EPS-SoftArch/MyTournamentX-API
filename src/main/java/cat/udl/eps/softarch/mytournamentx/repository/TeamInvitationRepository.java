package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface TeamInvitationRepository extends PagingAndSortingRepository<TeamInvitation, String> {
    boolean existsById(String id);
    TeamInvitation findTTeamInvitationById(String id);
    List<TeamInvitation> findAllTeamInvitationsByCreationDate(Date date);
}
