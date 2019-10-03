package cat.udl.eps.softarch.mytournamentx.config;

import cat.udl.eps.softarch.mytournamentx.domain.Player;
import cat.udl.eps.softarch.mytournamentx.domain.TournamentMaster;
import cat.udl.eps.softarch.mytournamentx.domain.User;
import cat.udl.eps.softarch.mytournamentx.repository.PlayerRepository;
import cat.udl.eps.softarch.mytournamentx.repository.TournamentMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Value("${default-password}")
  String defaultPassword;

  @Autowired
  BasicUserDetailsService basicUserDetailsService;

  @Autowired
  PlayerRepository playerRepository;

  @Autowired
  TournamentMasterRepository tournamentMasterRepository;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(basicUserDetailsService)
        .passwordEncoder(User.passwordEncoder);

    // Sample player
    if (!playerRepository.existsById("demoP")) {
      Player player = new Player();
      player.setEmail("demoP@mytournamentx.game");
      player.setUsername("demoP");
      player.setPassword(defaultPassword);
      player.encodePassword();
      playerRepository.save(player);
    }

    // Sample Tournament Master
    if (!tournamentMasterRepository.existsById("demoTM")) {
      TournamentMaster master = new TournamentMaster();
      master.setEmail("demoTM@mytournamentx.game");
      master.setUsername("demoTM");
      master.setPassword(defaultPassword);
      master.encodePassword();
      tournamentMasterRepository.save(master);
    }
  }
}
