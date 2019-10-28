package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitationId;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface TeamInvitationRepository extends PagingAndSortingRepository<TeamInvitation, TeamInvitationId> {
    boolean existsById(TeamInvitationId id);
    TeamInvitation findTTeamInvitationById(TeamInvitationId id);
    List<TeamInvitation> findAllTeamInvitationsByTeamId(String teamId);
    List<TeamInvitation> findAllTeamInvitationsByUserId(String userId);
    List<TeamInvitation> findAllTeamInvitationsByCreationDate(Date date);
    List<TeamInvitation> findAllTeamInvitationsByCreationDateRange(Date minDate, Date maxDate);
}
