package soft.onetech_dictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/dictionaries")
                        .permitAll()
                    .antMatchers("/dictionaries/**")
                        .hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/dictionaries/add")
                        .hasRole("USER")
                    .antMatchers(HttpMethod.DELETE, "/dictionaries/delete")
                        .hasRole("USER")
                    .anyRequest()
                        .authenticated()
                .and()
                    .httpBasic();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails adilbek = User.builder()
                .username("Adilbek")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails anonymous = User.builder()
                .username("Anonymous")
                .password(passwordEncoder().encode("password"))
                .roles("nobody")
                .build();

        return new InMemoryUserDetailsManager(
                adilbek,
                anonymous
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
