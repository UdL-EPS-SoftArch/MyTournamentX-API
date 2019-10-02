package cat.udl.eps.softarch.mytournamentx.domain;

import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import org.hibernate.validator.constraints.Length;


import java.time.ZonedDateTime;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Tournament extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    enum Level{
        BEGINNER,
        AMATEUR,
        PROFESSIONAL
    }

    @NotBlank
    private Level level;

    @NotBlank
    @Length(min = 5, max = 20)
    private String name;

    @NotBlank
    private String game;

    @Length(min = 1, max = 30)
    private String type;

    @Length(min = 1, max = 250)
    private String description;

    @Positive
    @Min(2)
    private Integer minParticipants;

    @Positive
    @Max(250)
    private Integer maxParticipants;

    @Positive
    @Min(1)
    private Integer minTeamPlayers;

    @Positive
    @Max(50)
    private Integer maxTeamPlayers;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime limitDate;

    public Tournament(Level level, @NotBlank @Length(min = 5, max = 20) String name, @NotBlank String game,
                      String type, String description, @NotBlank Integer minParticipants, Integer maxParticipants,
                      Integer minTeamPlayers, Integer maxTeamPlayers, ZonedDateTime limitDate) {

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
