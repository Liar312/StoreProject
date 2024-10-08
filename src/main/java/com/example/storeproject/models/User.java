package com.example.storeproject.models;

import com.example.storeproject.models.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {//интерфейс из springsecurity

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="email", unique = true)
    private String email;//используется как username
    @Column(name="Phone Number",unique = true)
    private String phoneNumber;
    @Column(name="password",length = 1000)
    private String password;
    private boolean active;
    private String name;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image avatar;


    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)//используем когда хотим сохранить коллекцию которая не является сущностями в бд
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name="user_id"))//указываем имя таблицЫ и стобца который отвечает за соединение элементов таблицы с основной коллекцией
    @Enumerated(EnumType.STRING)
    private Set<Role> roles =new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    private List<Product> products = new ArrayList<>();
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }
}
