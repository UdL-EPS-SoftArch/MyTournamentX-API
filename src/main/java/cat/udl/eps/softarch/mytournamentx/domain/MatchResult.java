package cat.udl.eps.softarch.mytournamentx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import cat.udl.eps.softarch.mytournamentx.repository.MatchResultRepository;
import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MatchResult extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;


    @NotBlank
    @Length(min = 1, max = 256)
    private String winner;


    @Length(min = 1, max = 256)
    private String description;

    @NotBlank
    @ManyToOne
    private Match match;

    @NotBlank
    @OneToMany
    private Team team;

    public MatchResult(@NotBlank @Length(min = 1, max = 256)
                               String winner, @Length(min = 1, max = 256) String description,@NotBlank Match match,
                       @NotBlank Team team){

        this.winner = winner;
        this.description = description;
        this.team=team;
    }
}
