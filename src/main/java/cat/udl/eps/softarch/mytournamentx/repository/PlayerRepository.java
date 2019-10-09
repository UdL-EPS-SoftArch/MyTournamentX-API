package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * The interface Player repository.
 */
@RepositoryRestResource
public interface PlayerRepository extends PagingAndSortingRepository<Player, String> {
    /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
     * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
     *
     * Additional methods following the syntax defined in
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */

    /**
     * Find a player by an e-mail.
     *
     * @param email email from the user
     * @return a player that matches the email
     */
    Player findByEmail(String email);

    /**
     * Find a list of players which matches the text.
     *
     * @param text the text to search
     * @return the list of players
     */
    List<Player> findByUsernameContaining(@Param("text") String text);
}
