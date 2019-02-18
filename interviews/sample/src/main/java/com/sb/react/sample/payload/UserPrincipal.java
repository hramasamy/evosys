package com.sb.react.sample.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.react.sample.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private Long id ;
    private String name ;
    private String username ;
    @JsonIgnore
    private String email ;
    @JsonIgnore
    private String password ;
    private Collection<? extends GrantedAuthority> authorities ;

    public UserPrincipal(Long id, String name, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id ;
        this.name = name ;
        this.username = username ;
        this.email = email ;
        this.password = password ;
        this.authorities = authorities ;
    }

    public static UserPrincipal create (User user) {
        List<GrantedAuthority> authorties =  user.getRoles().stream().map(role ->
        new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

        return new UserPrincipal(user.getId(), user.getName(), user.getUsername(), user.getEmail(),
                user.getPassword(), authorties) ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true ;
        if (o == null || getClass() != o.getClass()) return false ;
        UserPrincipal that = (UserPrincipal) o ;
        return Objects.equals(id, that.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) ;
    }
}
