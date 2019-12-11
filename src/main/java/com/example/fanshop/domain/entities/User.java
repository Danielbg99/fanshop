package com.example.fanshop.domain.entities;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "selledBy")
    private Set<FanShop> sellingArticles;
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Role> authorities;


    public User() {
    }


    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    public void setAccountNonExpired() {

    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    public void setAccountNonLocked() {

    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    public void setCredentialsNonExpired() {

    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public void setEnabled() {

    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<FanShop> getSellingArticles() {
        return sellingArticles;
    }

    public void setSellingArticles(Set<FanShop> sellingArticles) {
        this.sellingArticles = sellingArticles;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
}