package com.rdev.userregistery.repository;


import com.rdev.userregistery.entity.RegistryUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository interface for accessing RegistryUsers data
public interface RegistryRepo extends JpaRepository<RegistryUsers, Integer> {

 // Method to find a user by email
 Optional<RegistryUsers> findByEmail(String Email);
}
