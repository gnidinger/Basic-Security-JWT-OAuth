package back.global.config.security.userDetails;

import back.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser extends User implements UserDetails {

    private Long id;
    private String userId;
    private String password;
    private List<String> roles;
    private String nickname;

    private AuthUser(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.roles = List.of(user.getAuthType().toString());
        this.nickname = user.getNickname();
    }

    private AuthUser(Long id, List<String> roles) {
        this.id = id;
        this.password = "";
        this.roles = roles;
    }

    public static AuthUser of(User user) {
        return new AuthUser(user);
    }

    public static AuthUser of(Long id, List<String> roles) {
        return new AuthUser(id, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.get(0)));
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
