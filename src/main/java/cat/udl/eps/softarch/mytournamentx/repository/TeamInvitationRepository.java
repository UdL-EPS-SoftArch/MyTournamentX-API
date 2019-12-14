package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.TeamInvitation;
import cat.udl.eps.softarch.mytournamentx.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface TeamInvitationRepository extends PagingAndSortingRepository<TeamInvitation, Long> {
    boolean existsById(Long id);
    TeamInvitation findTTeamInvitationById(Long id);
    TeamInvitation findTTeamInvitationByTeamAndUser(Team team, User user);
    List<TeamInvitation> findAllTeamInvitationsByCreationDate(Date date);
}
