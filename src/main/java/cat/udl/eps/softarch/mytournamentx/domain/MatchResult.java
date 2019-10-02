package cat.udl.eps.softarch.mytournamentx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

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

    @ManyToOne
    private Match match;

    public MatchResult(@NotBlank @Length(min = 1, max = 256) String winner, @Length(min = 1, max = 256) String description){
        this.winner = winner;
        this.description = description;
    }

    public String getWinner() {
        return winner;
    }
    public String getDescription(){
        return  description;
    }

}
