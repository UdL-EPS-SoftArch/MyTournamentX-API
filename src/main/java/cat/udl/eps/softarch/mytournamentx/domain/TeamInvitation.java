package cat.udl.eps.softarch.mytournamentx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class TeamInvitation extends UriEntity<String> {

    @Id
    private String id;

    private String message;

    private Boolean accepted;

    private Date creationDate;
    public TeamInvitation(){}
    public TeamInvitation(String userId, String teamId,  String message){

        this.id = userId + "_" + teamId;
        this.message = message;
        this.creationDate = new Date();
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
    public String getId() {
        return id;
    }
}
