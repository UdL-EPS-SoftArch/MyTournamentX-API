package cat.udl.eps.softarch.mytournamentx.domain;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class TeamInvitationId implements Serializable {
    @NotBlank
    private String teamId;

    @NotBlank
    private String userId;

    public TeamInvitationId(String teamId, String userId){
        this.teamId = teamId;
        this.userId = userId;
    }
    @Override
    public boolean equals(Object object){
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        TeamInvitationId teamInvitationId = (TeamInvitationId) object;
        if(teamInvitationId.teamId == null || teamId == null){
            if(teamId != null || teamInvitationId.teamId != null)
                return false;
        }
        else{
            if (!teamInvitationId.teamId.equals(teamId))
                return false;
        }
        if(teamInvitationId.userId == null || userId == null){
            return userId == null && teamInvitationId.userId == null;
        }
        else{
            return teamInvitationId.userId.equals(userId);
        }
    }
}
