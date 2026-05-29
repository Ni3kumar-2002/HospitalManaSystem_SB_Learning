package com.example.HospitalManaSystem.Security;

import com.example.HospitalManaSystem.Entity.User;
import com.example.HospitalManaSystem.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j  //logging framework for logging
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request: {}", request.getRequestURL());
        final String requestTokenHeader = request.getHeader("Authorization");

//    Authorization header hi nahi aaya or Header to aaya but proper JWT format me nahi h.
        //if simple signUp ki request h to usme to JWTtoken hoga nhi
        //to request will be forwarded to next filterchain by filterChain.doFilter(request,response);
        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")){
//            Filter skips auth and forwards request.
            filterChain.doFilter(request,response);
            return;
        }

        String JWTtoken = requestTokenHeader.split("Bearer ")[1];
        String username = authUtil.getUsernameFromToken(JWTtoken);

//        Because hum authentication tabhi set karna chahte h jab:
//        username mila ho
//        AND security context empty ho

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //kuki hum security context ab fill krege
            User user = userRepository.findByUsername(username).orElseThrow();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //Now we created the filter, Now add this filter to filterChain
            //by putting it in WebsecurityConfig

        }


//      Otherwise forward the request
        filterChain.doFilter(request, response);  //going forward
    }

}

//By the way, Filters ka hi kam hota h Security Context ko fill krna
//A place where Spring stores the details of the currently authenticated user for
// the current request/thread.
//Agar request me JWT token nahi h
//ya Bearer format galat h
//
//to authentication mat karo,
//request ko aage bhej do.
//
//Aage Spring Security check karega:
//
//endpoint public h ?
//authentication required h ?
//user authenticated h ?

//POST /api/auth/login HTTP/1.1
//Host: localhost:8080
//Content-Type: application/json
//Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...(JWT)


//then we callrequest.getHeader("Auth")