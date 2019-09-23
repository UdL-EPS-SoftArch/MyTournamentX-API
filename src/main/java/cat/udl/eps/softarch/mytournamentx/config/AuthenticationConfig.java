package cat.udl.eps.softarch.mytournamentx.config;

import cat.udl.eps.softarch.mytournamentx.domain.User;
import cat.udl.eps.softarch.mytournamentx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Value("${default-password}")
  String defaultPassword;

  @Autowired BasicUserDetailsService basicUserDetailsService;
  @Autowired UserRepository userRepository;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(basicUserDetailsService)
        .passwordEncoder(User.passwordEncoder);

    // Sample user
    if (!userRepository.existsById("demo")) {
      User user = new User();
      user.setEmail("demo@mytournamentx.game");
      user.setUsername("demo");
      user.setPassword(defaultPassword);
      user.encodePassword();
      userRepository.save(user);
    }
  }
}
