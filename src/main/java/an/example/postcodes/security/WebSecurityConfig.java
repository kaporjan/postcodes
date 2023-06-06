package an.example.postcodes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Value("${admin.user}")
    private String user;
    @Value("${admin.password}")
    private String password;


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username(user)
                .password(password)
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((c) -> c.disable())
                .authorizeRequests((authorize) -> authorize
                        .requestMatchers("/postcodes").hasRole("ADMIN")
                        .requestMatchers("/postcodes/distance", "/error").permitAll()
                )
                .httpBasic();
        return http.build();
    }
}