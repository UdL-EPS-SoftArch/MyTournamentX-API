package cat.udl.eps.softarch.mytournamentx.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    private Boolean accepted;

    public TeamInvitation(String teamId, String userId, String message){
        this.teamId = teamId;
        this.userId = userId;
        this.message = message;
    }
    @Override
    public TeamInvitationId getId() {
        return new TeamInvitationId(teamId, userId);
    }

    public String getMessage() {
        return message;
    }

    public Boolean getAccepted(){
        return accepted;
    }

    public void setAccepted(Boolean accepted){
        this.accepted = accepted;
    }
}
