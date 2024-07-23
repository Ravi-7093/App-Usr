package com.rdev.userregistery.config;


import com.rdev.userregistery.service.RegistryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RegistryDetailsService registryDetailsService;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
       httpSecurity.csrf(AbstractHttpConfigurer::disable)         // Configure HTTP security
               .cors(Customizer.withDefaults())
               .authorizeHttpRequests(request-> request.requestMatchers("/auth/**", "/public/**").permitAll() // Define authorization rules
                       // Restrict access to admin endpoints to users with the ADMIN authority
                       .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                       .requestMatchers("/user/**").hasAnyAuthority("USER")
                       .requestMatchers("/adminuser/**").hasAnyAuthority("ADMIN", "USER")
                       .anyRequest().authenticated())
               .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider()).addFilterBefore(
                       jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
               );
        return httpSecurity.build();
    }
    //Handle verifying of user details from the db
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(registryDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // Return a new BCryptPasswordEncoder instance

        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}



