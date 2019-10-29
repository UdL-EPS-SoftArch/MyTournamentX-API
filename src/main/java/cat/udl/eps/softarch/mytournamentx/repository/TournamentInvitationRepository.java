package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.TournamentInvitation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TournamentInvitationRepository  extends PagingAndSortingRepository<TournamentInvitation, String> {

    boolean existsByMessage(String message);
    TournamentInvitation findTournamentInvitationByMessage(String message);

}
