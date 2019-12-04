package cat.udl.eps.softarch.mytournamentx.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Team extends UriEntity<String> {

    @NotBlank
    @Id
    @Length(min = 1, max = 256)
    private String name;

    @NotBlank
    @Length(min = 1, max = 256)
    private String game;

    @Length(min = 1, max = 256)
    private String level;

    @Max(value = 256) // Example
    @Min(value = 1)
    private int maxPlayers;

    @ManyToOne
    private Player leader;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set <Player> players;

    public Team(String name, String game, String level, int maxPlayers) {
        this.name = name;
        this.game = game;
        this.level = level;
        this.maxPlayers = maxPlayers;
        players = new HashSet<Player>();
    }

    public Team() {
    }

    @Override
    public String getId() {
        return name;
    }

    public Boolean userInTeam(Player player){
        return players.contains(player);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getCurrentPlayers() {
        return players.size();
    }

    public void setPlayer(Player player) {
         players.add(player);
    }
}
