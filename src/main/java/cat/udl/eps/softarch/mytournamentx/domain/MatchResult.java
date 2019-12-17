package cat.udl.eps.softarch.mytournamentx.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Team winner;

    @Length(min = 1, max = 256)
    private String description;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Match match;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Team sender;

}
