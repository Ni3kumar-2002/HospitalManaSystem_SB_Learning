package com.example.HospitalManaSystem.Security;
import com.example.HospitalManaSystem.dto.LoginResposeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final AuthService authService;
    private final ObjectMapper objectMapper;


    @Override
    //Spring automatically passes 3 things:
    //Authentication keeps complete security information of authenticated user.
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String registrationId = token.getAuthorizedClientRegistrationId();

        ResponseEntity<LoginResposeDto> loginRespone = authService.handleOAuth2LoginRequest(oAuth2User,registrationId);

        //Response
        response.setStatus(loginRespone.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(loginRespone.getBody()));
    }
}
//
//Method	Meaning
//getPrincipal()	logged-in user
//getAuthorities()	roles/permissions
//isAuthenticated()	authenticated or not
//getName()	username/name
//getDetails()	extra request details
//getCredentials()	password/token (sometimes null)
