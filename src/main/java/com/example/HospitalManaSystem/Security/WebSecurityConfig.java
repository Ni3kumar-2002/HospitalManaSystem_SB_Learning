package com.example.HospitalManaSystem.Security;

import com.example.HospitalManaSystem.Entity.type.*;
import com.example.HospitalManaSystem.Entity.type.RoleType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Configuration
/*
✅ @Configuration
Tells Spring: this class contains bean definitions
Spring will manage objects (beans) from here
 */
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;
    //You can specify Permission/authorities here

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity  //springboot jo by default service provide krti thi csrf &session management, we disabled that because session management jwt ke through hoga stateless
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/public/**").permitAll() //: anyone can access the endPoint
                        .requestMatchers(HttpMethod.DELETE,"/admin/**").hasAnyAuthority(PermissionType.APPOINTMENT_DELETE.name())
                        .requestMatchers("/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                        .anyRequest().authenticated() // authenticated :- only logged-in user can access the endpoint
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) //add this before username....
                //You can define your own login page here
                .oauth2Login(oAuth2 -> oAuth2
                        .failureHandler(
                        (request, response, exception) -> {
                            log.error("OAuth2 error: {}", exception.getMessage());
                        })
                        .successHandler(oAuth2SuccessHandler)  //see comment downwards

                )
                .exceptionHandling(exceptionConfig ->
                        exceptionConfig.accessDeniedHandler(new AccessDeniedHandler() {
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
                                //It will forward the IOException, ServletException to GlobalExceptionHandler
                                //where we are handling all type of exception
                            }
                        })

                );

//                .formLogin(Customizer.withDefaults());  this is when we uses default spring formlogin
                return httpSecurity.build();
                /*
                Builds the SecurityFilterChain
                This chain intercepts every request
                 */
    }
}
//.successHandler(oAuth2SuccessHandler)
//        ↓
//Spring stores object as:
//AuthenticationSuccessHandler
//        ↓
//Interface guarantees:
//onAuthenticationSuccess() exists
//        ↓
//Login successful
//        ↓
//Spring calls:
//onAuthenticationSuccess(...)

/*
singleton beans are created in @Configuration
👉 In Spring, a singleton bean means:

Only one object (instance) of that class is created in the Spring container
and shared everywhere in the application.

One class → One object → Used everywhere


 */