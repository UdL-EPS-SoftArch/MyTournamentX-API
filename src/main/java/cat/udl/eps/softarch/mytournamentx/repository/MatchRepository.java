package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * The interface Player repository.
 */
@RepositoryRestResource
public interface MatchRepository extends PagingAndSortingRepository<Match, String> {
    /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
     * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
     *
     * Additional methods following the syntax defined in
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */

    /**
     * Find a list of match results.
     *
     * @return the list of match results
     */
    // List<MatchResult> findByMatch(@Param("match") Match match);

    /**
     * Find a Match by id.
     *
     * @return match identified by the given id.
     */
    Match findById(@Param("id") Integer id);
}
