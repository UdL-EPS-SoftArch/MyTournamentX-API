package cat.udl.eps.softarch.mytournamentx.domain;

import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


import java.util.Date;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Tournament extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @NotBlank
    private String level;

    @NotBlank
    @Length(min = 5, max = 20)
    private String name;

    @NotBlank
    private String game;

    private String type;
    private String description;

    @NotBlank
    private Integer minParticipants;

    @NotBlank
    private Integer maxParticipants;

    @NotBlank
    private Integer minTeamPlayers;

    @NotBlank
    //Comprovar maxim!!
    private Integer maxTeamPlayers;

    private Date limitDate;

    public Tournament(@NotBlank String level, @NotBlank @Length(min = 5, max = 20) String name, @NotBlank String game, String type, String description, @NotBlank Integer minParticipants, Integer maxParticipants, Integer minTeamPlayers, Integer maxTeamPlayers, Date limitDate) {
        this.level = level;
        this.name = name;
        this.game = game;
        this.type = type;
        this.description = description;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.minTeamPlayers = minTeamPlayers;
        this.maxTeamPlayers = maxTeamPlayers;
        this.limitDate = limitDate;
    }

    public void setParticipants(Integer minParticipants, Integer maxParticipants){
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
    }

    public void setTeamPlayers(Integer minTeamPlayers, Integer maxTeamPlayers){
        this.minTeamPlayers = minTeamPlayers;
        this.maxTeamPlayers = maxTeamPlayers;
    }




}
