package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository <Team, String> {

   boolean existsByName(String name);
   Team findTeamByName(String name);
}
