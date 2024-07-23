package com.rdev.userregistery.service;


import com.rdev.userregistery.repository.RegistryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RegistryDetailsService implements UserDetailsService {

    @Autowired
    private RegistryRepo registryRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return registryRepo.findByEmail(username).orElseThrow();

    }
}
