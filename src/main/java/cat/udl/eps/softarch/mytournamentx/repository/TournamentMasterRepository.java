package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.TournamentMaster;
import cat.udl.eps.softarch.mytournamentx.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TournamentMasterRepository extends PagingAndSortingRepository<User, String> {
        /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
     * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
     *
     * Additional methods following the syntax defined in
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */

    /**
     * Find a TournamentMaster by an e-mail.
     *
     * @param email email from the user
     * @return a player that matches the email
     */
    TournamentMaster findByEmail(String email);

    /**
     * Find a list of TournamentMasters which matches the text.
     *
     * @param text the text to search
     * @return the list of TournamentMasters
     */
    List<TournamentMaster> findByUsernameContaining(@Param("text") String text);
}
