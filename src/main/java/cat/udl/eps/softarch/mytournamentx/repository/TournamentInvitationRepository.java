package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.TournamentInvitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RepositoryRestResource
public interface TournamentInvitationRepository  extends PagingAndSortingRepository<TournamentInvitation, String> {

    boolean existsByMessage(String message);
    TournamentInvitation findTournamentInvitationByMessage(String message);

    List<TournamentInvitation> findByMessageContainingIgnoreCase(@Param("text") String text);
    List<TournamentInvitation> findById(@Param("id") long id);

    @PreAuthorize("#invites.username == principal.username")
    Page<TournamentInvitation> findByInvites(@Param("invites") Player invites, Pageable p);

    @PreAuthorize("#createdBy.username == principal.username")
    Page<TournamentInvitation> findByCreatedBy(@Param("createdBy") Player createdBy, Pageable p);

}
