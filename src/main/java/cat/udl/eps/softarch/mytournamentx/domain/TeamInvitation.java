package cat.udl.eps.softarch.mytournamentx.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class TeamInvitation extends UriEntity<Long> {

    @Id @GeneratedValue(strategy=GenerationType.AUTO) long id;

    @ManyToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @ManyToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private User creationUser;

    @ManyToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private Team team;
    private String message;

    private Boolean accepted;

    private Date creationDate;
    public TeamInvitation(){}

    public User getUser() {
        return user;
    }

    public User getCreationUser() {
        return creationUser;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamInvitation(User user, Team team, String message){

        this.user = user;
        this.team = team;
        this.message = message;
        this.creationDate = new Date();
        creationUser = ((Player)authentication.getPrincipal());
    }

    public String getMessage() {
        return message;
    }

    public Boolean getAccepted(){
        return accepted;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public void setAccepted(Boolean accepted){
        this.accepted = accepted;
    }

    @Override
    public Long getId() {
        return id;
    }
}
