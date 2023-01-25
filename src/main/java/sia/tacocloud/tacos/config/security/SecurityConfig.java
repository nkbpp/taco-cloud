package sia.tacocloud.tacos.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sia.tacocloud.tacos.repository.UserRepository;

@Configuration
public class SecurityConfig {

    /***
     * Для шифрования паролей
     * BCryptPasswordEncoder – применяет надежное шифрование bcrypt;
     * NoOpPasswordEncoder – не применяет шифрования;
     * Pbkdf2PasswordEncoder – применяет шифрование PBKDF2;
     * SCryptPasswordEncoder – применяет шифрование Scrypt;
     * StandardPasswordEncoder – применяет шифрование SHA-256. (Устарел)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /***
     * UserDetailsService - хранилище учетных записей пользователей
     */
/*    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> usersList = new ArrayList<>();
        usersList.add(
                new User(
                        "buzz", encoder.encode("password"),
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
        usersList.add(
                new User(
                        "woody", encoder.encode("password"),
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
        return new InMemoryUserDetailsManager(usersList);
    }*/
    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> {
            var user = repository.findByUsername(username).orElse(null);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException("User ‘" + username + "’ not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/design", "/orders").hasRole("USER")
                .requestMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .build();
    }

}
