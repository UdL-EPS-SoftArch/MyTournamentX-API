package cat.udl.eps.softarch.mytournamentx.domain;


import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
public class Team extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /*OWN CODE*/
    @NotBlank
    @Length(min = 1, max = 256)
    private String name;

    @NotBlank
    @Length(min = 1, max = 256)
    private String game;

    /*enum Level {
        Beginner,
        Amateur,
        Professional
    }*/
    @Length(min = 1, max = 256)
    private String level;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
