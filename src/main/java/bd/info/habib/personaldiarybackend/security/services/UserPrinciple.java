package bd.info.habib.personaldiarybackend.security.services;

import bd.info.habib.personaldiarybackend.model.Role;
import bd.info.habib.personaldiarybackend.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class UserPrinciple implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    private final String name;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private Set<Role> roles;

    public static UserPrinciple build(UserModel user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());


        return new UserPrinciple(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                authorities,
                user.getRoles()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
