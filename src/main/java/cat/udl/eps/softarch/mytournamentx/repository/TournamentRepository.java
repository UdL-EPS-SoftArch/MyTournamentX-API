package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.domain.Tournament;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TournamentRepository extends PagingAndSortingRepository<Tournament, String> {

    boolean existsByName(String name);
    Tournament findTournamentByName(String name);

    List<Team> findAllByName(Tournament tournament);
    List<Team> findParticipantsByName(String tournament);
}
