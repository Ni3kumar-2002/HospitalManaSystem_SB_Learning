package com.example.HospitalManaSystem.Repository;

import com.example.HospitalManaSystem.Entity.User;
import com.example.HospitalManaSystem.Entity.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}

/*
Optional is a container object that may or may not contain a value.

👉 It is used to avoid NullPointerException.

Agar user nahi mila, to Optional me:

Optional.empty()

store hota hai ✔️

User user = repo.findById(1); // may return null ❌

We use:

Optional<User> user = repo.findById(1); // safer ✔️

👉 Now:

Value ho bhi sakti hai ✔️
Nahi bhi ho sakti ❌


  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();  //yha pr orElseThow() uncommon from video
    }

    UserRepository Optional<User> return kr rhi h pr user ne hi
    UserDetails ko implements kr rakha h to koi issue nhi

    Repository m humne optional mention krke btaya ki user may or may not contains a value
    so we need to handle this in above method too by mention orElseThrow(), when user
    wouldn't have value throw Exception
 */