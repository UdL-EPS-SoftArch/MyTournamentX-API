package cat.udl.eps.softarch.mytournamentx.repository;

import cat.udl.eps.softarch.mytournamentx.domain.MatchResult;
import cat.udl.eps.softarch.mytournamentx.domain.Match;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchResultRepository extends PagingAndSortingRepository<MatchResult, String> {

    List<MatchResult> findByMatchContaining(@Param("match") Match match);

    MatchResult findbyMatchAndLeadPlayer(@Param("match") Match match, @Param)

    List<MatchResult> findByWinnerContaining(@Param("winner") String winner);

    List<MatchResult> findByDescriptionContaining(@Param("description") String description);

}
