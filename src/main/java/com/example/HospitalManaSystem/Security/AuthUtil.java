package com.example.HospitalManaSystem.Security;

import com.example.HospitalManaSystem.Entity.User;
import com.example.HospitalManaSystem.Entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")    //value injection//from application.properties
    private String jwtSecretKey;

    //SecretKey is an interface
    //generate SecretKey object
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    //generate JWT token  //return string
    public String generateAccessToken(User user){  //user comes from where method gonna use
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey()) //secretKey se jo algo aaegi usse it'll create header
                .compact();
    }

    public String getUsernameFromToken(String jwTtoken) {
        Claims claims =  Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwTtoken)
                .getPayload();

        //Now you can extract multiple info from this claims
        //by which you created JWT logiv
        return claims.getSubject();

    }

    public AuthProviderType getProviderTypeFromRegistrationID(String registrationId) {
        return switch (registrationId.toLowerCase()){
            case "google" -> AuthProviderType.GOOGLE;
            case "github" -> AuthProviderType.GITHUB;
            case "facebook" -> AuthProviderType.FACEBOOK;
            default -> throw new IllegalArgumentException("Unsupported 0Auth2 Provider" + registrationId);
        };
    }

    public String getProviderIdFrom0Auth2User(OAuth2User oAuth2User, String registrationId) {
        String providerId = switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("Unsupported Oauth2 provider", registrationId);
                throw new IllegalArgumentException("Unsupported Oauth2 provider" + registrationId);
            }
        };
        //cover all cases
        if(providerId == null || providerId.isBlank()){
            log.error("Unable to determine providerID for provider: {}", registrationId);
            throw new IllegalArgumentException("Unable to determine providerId for OAuth2 login");
        }
        return providerId;
    }

    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId){
        String email = oAuth2User.getAttribute("email");
        if(email!=null && !email.isBlank()){
            return email;
        }
        return switch(registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id");
            default -> providerId;
        };
        //i am just coping the code otherwise know, registrationid se hi providerId find kr chuka hu already
        //yha provider Id ka jada kuch sense nhi h
    }
    //JwtBuilder ko jwt token bnane ke liye sb provide kr diya h vo jwt token bna lega
//when I send username and password

// Header
//{
//  "alg": "HS384"
//}

//    {  //Payload
//        "sub": "Nitin",
//            "userId": "1",
//            "iat": 1778169035,
//            "exp": 1778169635
//    }






}
