package cat.udl.eps.softarch.mytournamentx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class TournamentInvitation {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    public TournamentInvitation(@NotBlank @Length(min = 1, max = 250) String message) {
        this.message = message;
    }

    @NotBlank
    @Length(min = 1, max = 250)
    private String message;


    public TournamentInvitation(){}

    public void setTournamentInvitation(Integer id, String message){
        this.id = id;
        this.message = message;
    }
}
