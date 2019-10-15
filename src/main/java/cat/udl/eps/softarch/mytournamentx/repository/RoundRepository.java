package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.Round;
import cat.udl.eps.softarch.mytournamentx.domain.Team;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * The interface Round repository.
 */
@RepositoryRestResource
public interface RoundRepository extends PagingAndSortingRepository<Round, String> {
    /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
     * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
     *
     * Additional methods following the syntax defined in
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */

    /**
     * Find a Round by id.
     *
     * @return round identified by the given id.
     */
    Round findById(@Param("id") Integer id);

    /**
     * Find a list of rounds won by a particular team.
     *
     * @return a list of rounds.
     */
    List<Round> findByWinner(@Param("winner") Team winner);

    /**
     * Find a list of matches won by a particular team.
     *
     * @return a list of matches.
     */
    // List<Match> findMatchesByWinner(@Param("winner") Team winner);
}
