package cat.udl.eps.softarch.mytournamentx.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Team extends UriEntity<String> {

    /*OWN CODE*/
    @NotBlank
    @Id
    @Length(min = 1, max = 256)
    private String name;

    //@NotBlank
    //@Length(min = 1, max = 256)
    private String game;


    //@Length(min = 1, max = 256)
    private String level;


    //@Max(value = 8) // Example
    //@Min(value = 0)
    private int maxPlayers;

    @ManyToOne
    private Player leader;

    @ManyToMany
    private Set <Player> players;

    public Team(String name,String game,String level, int maxPlayers) {
        this.name = name;
        this.game = game;
        this.level = level;
        this.maxPlayers = maxPlayers;
    }

    public Team() {
    }

    @Override
    public String getId() {
        return name;
    }

    public Boolean userInTeam(String userId){
        return players.contains(userId);
    }
}
