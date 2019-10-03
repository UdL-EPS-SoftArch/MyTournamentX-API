package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.Team;
import cat.udl.eps.softarch.mytournamentx.repository.TeamRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;

public class CreateTeamStepDefs {


    @Autowired private TeamRepository teamRepository;

    @Given("^There is no registered team with name \"([^\"]*)\"$")
    public void thereIsNoRegisteredTeamWithName(String arg0) throws Throwable {

        Assert.assertFalse(teamRepository.existsByName(arg0));
    }

    @Given("^I register a new team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void iRegisterANewTeamWithNameGameLevelMaxPlayers(String arg0, String arg1, String arg2, int arg3) throws Throwable {

        if(!teamRepository.existsByName(arg0)){
            Team team = new Team(arg0,arg1,arg2,arg3);
            Assert.assertEquals(team.getName(),"team");
            teamRepository.save(team);
        }
    }

    @Then("^It has been created a team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+)$")
    public void itHasBeenCreatedATeamWithNameGameLevelMaxPlayers(String arg0, String arg1, String arg2, int arg3) throws Throwable {
        Team team = teamRepository.findTeamByName(arg0);
        Assert.assertEquals(team.getName(),arg0);
        Assert.assertEquals(team.getGame(),arg1);
        Assert.assertEquals(team.getLevel(),arg2);
        Assert.assertEquals(team.getMaxPlayers(),arg3);
    }
}
