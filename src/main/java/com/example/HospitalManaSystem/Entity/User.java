package com.example.HospitalManaSystem.Entity;

import com.example.HospitalManaSystem.Entity.type.AuthProviderType;
import com.example.HospitalManaSystem.Entity.type.RoleType;
import com.example.HospitalManaSystem.Security.RolePermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name= "users", indexes = {
        @Index(name = "idx_provider_id&type", columnList = "providerId, providerType")
}) //in postgre USER is a resered keyword so we....
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(unique = true)
    private String username;
    private String password;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    //Defining User roles in User
    //User can have multiple role so set

    //  @ElementCollection : These are stored in a separate table,
    // but they are tightly owned by the parent entity.
    // eager means when we fetch an user....its roles would be fetched.
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles= new HashSet<>();
    //This roles table will store:
    //user_id (foreign key)
    //roles (the values)

    @Override          //here we will mention all the authorities
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
//                .collect(Collectors.toSet());


        //by this you can apply method level security  //EnableMethodSecurity in webconfig file
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    //User ke role ke according sari authorities store hogi
                    //Permission + ROLE_role
                    Set<SimpleGrantedAuthority> permissions= RolePermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );
        return authorities;
    }


}
