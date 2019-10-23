package cat.udl.eps.softarch.mytournamentx.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.TournamentMaster;
import cat.udl.eps.softarch.mytournamentx.domain.User;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentMasterRepository;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class RegisterStepDefs {

  @Autowired
  private StepDefs stepDefs;

  @Autowired
  private TournamentMasterRepository tournamentMasterRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Given("^There is no registered tournamentmaster with username \"([^\"]*)\"$")
  public void thereIsNoRegisteredTournamentmasterWithUsername(String master) {
    Assert.assertFalse("Tornament master \""
                     +  master + "\"shouldn't exist",
                     tournamentMasterRepository.existsById(master));
  }

  @Given("^There is a registered tournamentmaster with username \"([^\"]*)\" and password \"([^\"]*)\" and email \"([^\"]*)\"$")
  public void thereIsARegisteredTournamentmasterWithUsernameAndPasswordAndEmail(String username, String password, String email) {
    if (!tournamentMasterRepository.existsById(username)) {
      TournamentMaster master = new TournamentMaster();
      master.setEmail(email);
      master.setUsername(username);
      master.setPassword(password);
      master.encodePassword();
      tournamentMasterRepository.save(master);
    }
  }

  @Given("^There is no registered player with username \"([^\"]*)\"$")
  public void thereIsNoRegisteredPlayerWithUsername(String player) {
    Assert.assertFalse("Player \""
                    +  player + "\"shouldn't exist",
                    playerRepository.existsById(player));
  }

  @Given("^There is a registered player with username \"([^\"]*)\" and password \"([^\"]*)\" and email \"([^\"]*)\"$")
  public void thereIsARegisteredPlayerWithUsernameAndPasswordAndEmail(String username, String password, String email) {
    if (!playerRepository.existsById(username)) {
      Player player = new Player();
      player.setEmail(email);
      player.setUsername(username);
      player.setPassword(password);
      player.encodePassword();
      playerRepository.save(player);
    }
  }

  @When("^I register a new tournamentmaster with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void iRegisterANewTournamentmasterWithUsernameEmailAndPassword(String username, String email, String password) throws Throwable {
    TournamentMaster master = new TournamentMaster();
    master.setUsername(username);
    master.setEmail(email);

    stepDefs.result = stepDefs.mockMvc.perform(
            post("/tournamentMasters")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new JSONObject(
                            stepDefs.mapper.writeValueAsString(master)
                    ).put("password", password).toString())
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .with(AuthenticationStepDefs.authenticate()))
            .andDo(print());
  }

  @And("^It has been created a tournamentmaster with username \"([^\"]*)\" and email \"([^\"]*)\", the password is not returned$")
  public void itHasBeenCreatedATournamentmaster(String username, String email) throws Throwable {
    stepDefs.result = stepDefs.mockMvc.perform(
            get("/tournamentMasters/{username}", username)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .with(AuthenticationStepDefs.authenticate()))
            .andDo(print())
            .andExpect(jsonPath("$.email", is(email)))
            .andExpect(jsonPath("$.password").doesNotExist());
  }

  @And("^It has not been created a tournamentmaster with username \"([^\"]*)\"$")
  public void itHasNotBeenCreatedATournamentmasterWithUsername(String username) throws Throwable {
    stepDefs.result = stepDefs.mockMvc.perform(
            get("/tournamentMasters/{username}", username)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .with(AuthenticationStepDefs.authenticate()))
            .andExpect(status().isNotFound());
  }

  @And("^I can login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void iCanLoginWithUsernameAndPassword(String username, String password) throws Throwable {
    AuthenticationStepDefs.currentUsername = username;
    AuthenticationStepDefs.currentPassword = password;

    stepDefs.result = stepDefs.mockMvc.perform(
        get("/identity", username)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .with(AuthenticationStepDefs.authenticate()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @And("^I cannot login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void iCannotLoginWithUsernameAndPassword(String username, String password) throws Throwable {
    AuthenticationStepDefs.currentUsername = username;
    AuthenticationStepDefs.currentPassword = password;

    stepDefs.result = stepDefs.mockMvc.perform(
        get("/identity", username)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .with(AuthenticationStepDefs.authenticate()))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @When("^I register a new player with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void iRegisterANewPlayerWithUsernameEmailAndPassword(String username, String email, String password) throws Throwable {
    Player player = new Player();
    player.setUsername(username);
    player.setEmail(email);

    stepDefs.result = stepDefs.mockMvc.perform(
            post("/players")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new JSONObject(
                            stepDefs.mapper.writeValueAsString(player)
                    ).put("password", password).toString())
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .with(AuthenticationStepDefs.authenticate()))
            .andDo(print());
  }

  @And("^It has been created a player with username \"([^\"]*)\" and email \"([^\"]*)\", the password is not returned$")
  public void itHasBeenCreatedAPlayerWithUsername(String username, String email) throws Throwable {
    stepDefs.result = stepDefs.mockMvc.perform(
            get("/players/{username}", username)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .with(AuthenticationStepDefs.authenticate()))
            .andDo(print())
            .andExpect(jsonPath("$.email", is(email)))
            .andExpect(jsonPath("$.password").doesNotExist());
  }

  @And("^It has not been created a player with username \"([^\"]*)\"$")
  public void itHasNotBeenCreatedAPlayerWithUsername(String username) throws Throwable {
    stepDefs.result = stepDefs.mockMvc.perform(
            get("/players/{username}", username)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .with(AuthenticationStepDefs.authenticate()))
            .andExpect(status().isNotFound());
  }
}
