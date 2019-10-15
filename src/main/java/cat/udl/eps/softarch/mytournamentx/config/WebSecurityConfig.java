package cat.udl.eps.softarch.mytournamentx.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Value("${allowed-origins}")
        String[] allowedOrigins;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/identity").authenticated()
                    .antMatchers(HttpMethod.POST, "/tournamentMasters").anonymous()
                    .antMatchers(HttpMethod.POST, "/players").anonymous()
                    .antMatchers(HttpMethod.POST, "/tournamentMasters/*").denyAll()
                    .antMatchers(HttpMethod.POST, "/players/*").denyAll()

                    .antMatchers(HttpMethod.GET, "/tournaments/*").authenticated()
                    .antMatchers(HttpMethod.GET, "/tournaments").authenticated()
                    .antMatchers(HttpMethod.PUT, "/tournaments/*").hasRole("TOURNAMENTMASTER")
                    .antMatchers(HttpMethod.POST, "/tournaments").hasRole("TOURNAMENTMASTER")
                    .antMatchers(HttpMethod.DELETE, "/tournaments/*").hasRole("TOURNAMENTMASTER")
              
                    .antMatchers(HttpMethod.GET, "/matches").authenticated()
                    .antMatchers(HttpMethod.POST, "/matches").denyAll()

                    .antMatchers(HttpMethod.GET, "/teams/*").anonymous()
                    .antMatchers(HttpMethod.PUT, "/teams/*").hasRole("PLAYER")
                    .antMatchers(HttpMethod.POST, "/teams").hasRole("PLAYER")
                    .antMatchers(HttpMethod.DELETE, "/teams/*").hasRole("PLAYER")

                    .antMatchers(HttpMethod.GET, "/matchresult/*").authenticated()
                    .antMatchers(HttpMethod.GET, "/matchresult").authenticated()
                    .antMatchers(HttpMethod.PUT, "/matchresult/*").hasRole("TeamLeader")
                    .antMatchers(HttpMethod.POST, "/matchresult").hasRole("TeamLeader")
                    .antMatchers(HttpMethod.DELETE, "/matchresult/*").hasRole("TOURNAMENTMASTER")

                    .antMatchers(HttpMethod.POST, "/**/*").authenticated()

                    .antMatchers(HttpMethod.POST, "/**/*").authenticated()
                    .antMatchers(HttpMethod.PUT, "/**/*").authenticated()
                    .antMatchers(HttpMethod.PATCH, "/**/*").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/**/*").authenticated()


                    .anyRequest().permitAll()
                    .and()
                    .httpBasic().realmName("MyTournamentX")
                    .and()
                    .cors()
                    .and()
                    .csrf().disable();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
            corsConfiguration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }

        @Bean
        public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
            return new SecurityEvaluationContextExtension();
        }
    }


