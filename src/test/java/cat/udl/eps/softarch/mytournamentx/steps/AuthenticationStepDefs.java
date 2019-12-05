package cat.udl.eps.softarch.mytournamentx.steps;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import cat.udl.eps.softarch.mytournamentx.domain.Match;
import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class AuthenticationStepDefs {

    public static String currentUsername;
    public static String currentPassword;

    @Before
    public void setup() {
        // Clear authentication credentials at the start of every test.
        AuthenticationStepDefs.currentPassword = "";
        AuthenticationStepDefs.currentUsername = "";
    }

    static RequestPostProcessor authenticate() {
        return currentUsername!=null ? httpBasic(currentUsername, currentPassword) : anonymous();
    }

    static 

    @Given("^I login as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLoginAsWithPassword(String username, String password) {
        AuthenticationStepDefs.currentUsername = username;
        AuthenticationStepDefs.currentPassword = password;
    }

    @Given("^I'm not logged in$")
    public void iMNotLoggedIn() {
        currentUsername = currentPassword = null;
    }


    @And("^I'm logged in like team's leader \"([^\"]*)\" who has played this Match\"([^\"]*)\"$")
    public void iMLoggedInLikeTeamSLeaderWhoHasPlayedThisMatch(Player teamLeader, Match match) throws Throwable {
        AuthenticationStepDefs.currentUsername = teamLeader.getUsername();
        AuthenticationStepDefs.currentPassword = teamLeader.getPassword();
        // How can we know teamLeader played the match?
        throw new PendingException();
    }
}
