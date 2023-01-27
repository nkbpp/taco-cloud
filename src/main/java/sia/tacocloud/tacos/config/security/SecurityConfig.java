package sia.tacocloud.tacos.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sia.tacocloud.tacos.repository.MyUserRepository;

@Configuration
@EnableMethodSecurity //@EnableGlobalMethodSecurity
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
    public UserDetailsService userDetailsService(MyUserRepository repository) {
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

        //отключает заголовки позволяет работать консоли h2
        http.csrf().disable();
        http.headers().disable();

        return http
                .authorizeHttpRequests()
                .requestMatchers("/design", "/orders").hasRole("USER")
                .anyRequest().permitAll()
                /*.requestMatchers("/", "/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()*/
                .and()
                .formLogin()
                .loginPage("/login")
                //.loginProcessingUrl("/authenticate") //запросы на вход будут иметь путь /authenticate
                //.usernameParameter("user") //имя пользователя будет передаваться в поле user
                //.passwordParameter("pwd") //пароль будет передаваться в поле pwd
                .defaultSuccessUrl("/design", true) //если  пользователь  напрямую  открыл страницу входа и успешно прошел аутентификацию, то он будет перенаправлен на страницу /design (можно принудительно перенаправить пользователя на страницу /designпосле входа, даже если он изначально пытался от-крыть другую страницу, передав trueво втором параметре)
                .and()
                /*// Make H2-Console non-secured; for debug purposes
                .csrf()
                .ignoringRequestMatchers("/h2-console/**")
                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()*/
                .build();


    }


}
