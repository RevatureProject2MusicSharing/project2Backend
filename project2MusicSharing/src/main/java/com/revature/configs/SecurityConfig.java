package com.revature.configs;

import com.revature.daos.UserDAO;
import com.revature.utils.JwtTokenFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDAO userDAO;

    //used for JWT token validation (We wrote this Class)
    private final JwtTokenFilter jwtTokenFilter;

    //used for authenticating users (Spring Security wrote this)
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    public SecurityConfig(UserDAO userDAO, JwtTokenFilter jwtTokenFilter, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userDAO = userDAO;
        this.jwtTokenFilter = jwtTokenFilter;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    //This method configures the authentication manager before trying to log the user in
    //We use the userDAO to find a user by username, or throw an exception
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            if(userDAO.findByUsername(username) == null){
                throw new UsernameNotFoundException("User " + username + " not found.");
            } else {
                return userDAO.findByUsername(username);
            }
        });
    }

    //after the authentication manager is configured in the method above,
    //we can use this method to get an authentication manager, which is used in the login method
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //we'll use this to encrypt our passwords, and any other sensitive info in addUser
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //This method is used to configure the security filters, session management, and URL privileges
    /* The most important things to note here are:
           -we set all requests to /auth or a POST /users to be accessible by anybody
                -only admins can access /users (except for POST)
                -admins and users can access any other endpoint (implied by anyRequest().authenticated())
           -we set all non login/register requests to require authentication (a valid JWT, gained from login)
           -we set the session management to be stateless (no session data is stored)
           -we also add our custom JWT filter to the filter chain*/
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {


        return http.csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth ->
                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/register","/login").permitAll()
                    .anyRequest().authenticated())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    //
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Specify allowed origin
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials if needed

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
