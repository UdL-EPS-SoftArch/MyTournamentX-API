package cat.udl.eps.softarch.mytournamentx.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class TournamentInvitation extends UriEntity<String>{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdAt;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Player invites;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Player createdBy;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Tournament invitesTo;

    @Lob
    @NotBlank
    @Size(max = 255)
    private String message;

    public TournamentInvitation(){}

    public void setTournamentInvitation(String id, String message){
        this.id = id;
        this.message = message;
    }

    @Override
    public String getId() {
        return id;
    }
}
