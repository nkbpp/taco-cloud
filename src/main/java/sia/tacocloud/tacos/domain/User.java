package sia.tacocloud.tacos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected final String username;
    protected final String password;
    protected final String fullname;
    protected final String street;
    protected final String city;
    protected final String state;
    protected final String zip;
    protected final String phoneNumber;

    /***
     * Метод getAuthorities() должен  возвращать  набор  привилегий
     * пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /***
     * Срок действия учетной записи не истек
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /***
     * Аккаунт не заблокирован
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /***
     * Срок действия учетных данных не истек
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /***
     * Включено
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
