package cat.udl.eps.softarch.mytournamentx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Round extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private int bestOf = 1;

    private int numTeams = 2;

    @ManyToOne
    private Team winner;

    @ManyToMany
    private List<Team> rivals;

    @OneToOne
    private Round nextRound;

    @ManyToOne
    private Tournament tournament;
}
