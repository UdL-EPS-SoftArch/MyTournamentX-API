package cat.udl.eps.softarch.mytournamentx.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Team extends UriEntity<String> {

    /*OWN CODE*/
    @NotBlank
    @Id
    @Length(min = 1, max = 256)
    private String name;

    @NotBlank
    @Length(min = 1, max = 256)
    private String game;


    @Length(min = 1, max = 256)
    private String level;

    @ManyToOne
    private Player teamLeader;

    @Max(value = 8) // Example
    @Min(value = 1)
    private int maxPlayers;


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
}
