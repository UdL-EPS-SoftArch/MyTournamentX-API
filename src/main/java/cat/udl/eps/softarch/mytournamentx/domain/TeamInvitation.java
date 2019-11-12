package cat.udl.eps.softarch.mytournamentx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity @IdClass(TeamInvitationId.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class TeamInvitation extends UriEntity<TeamInvitationId> {

    @Id
    @NotBlank
    private String teamId;

    @Id
    @NotBlank
    private String userId;

    private String message;

    private TeamInvitationId id;

    private Boolean accepted;

    private Date creationDate;

    public TeamInvitation(String teamId, String userId, String message){
        this.teamId = teamId;
        this.userId = userId;
        this.id = new TeamInvitationId(teamId, userId);
        this.message = message;
        this.creationDate = new Date();
    }
    @Override
    public TeamInvitationId getId() {
        return id;
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
}
