package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.Round;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * The interface Player repository.
 */
@RepositoryRestResource
public interface MatchRepository extends PagingAndSortingRepository<Match, Integer> {
    /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
     * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
     *
     * Additional methods following the syntax defined in
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */

    /**
     * Find a Match by id.
     *
     * @return match identified by the given id.
     */
    Optional<Match> findById(@Param("id") Integer id);

    /**
     * Find a Match by Round.
     *
     * @return match identified by the given Round.
     */
    List<Match> findByRound(@Param("round") Round round);
}
