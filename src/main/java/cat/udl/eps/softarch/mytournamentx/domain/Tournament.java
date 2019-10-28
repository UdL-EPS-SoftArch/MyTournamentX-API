package cat.udl.eps.softarch.mytournamentx.domain;

import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.ZonedDateTime;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Tournament extends UriEntity<String> {

    @Id
    @NotBlank
    @Length(min = 5, max = 20)
    private String name;

    @Override
    public String getId() {
        return name;
    }

    public enum Level{
        BEGINNER,
        AMATEUR,
        PROFESSIONAL
    }
    @NotNull(message = "Level must not be Null")
    private Level level;


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
    @Max(256)
    private Integer maxParticipants;

    @Positive
    @Min(1)
    private Integer minTeamPlayers;

    @Positive
    @Max(50)
    private Integer maxTeamPlayers;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime limitDate;

    public Tournament(@NotBlank @Length(min = 5, max = 20) String name, @NotBlank Level level, @NotBlank String game) {
        this.name = name;
        this.level = level;
        this.game = game;
    }

    public Tournament(){}

    public void setParticipants(Integer minParticipants, Integer maxParticipants){
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
    }

    public void setTeamPlayers(Integer minTeamPlayers, Integer maxTeamPlayers){
        this.minTeamPlayers = minTeamPlayers;
        this.maxTeamPlayers = maxTeamPlayers;
    }




}
