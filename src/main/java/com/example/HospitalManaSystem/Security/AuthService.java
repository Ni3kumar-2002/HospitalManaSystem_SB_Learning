package com.example.HospitalManaSystem.Security;

import com.example.HospitalManaSystem.Entity.Patient;
import com.example.HospitalManaSystem.Entity.User;
import com.example.HospitalManaSystem.Entity.type.AuthProviderType;
import com.example.HospitalManaSystem.Entity.type.RoleType;
import com.example.HospitalManaSystem.Repository.PatientRepo;
import com.example.HospitalManaSystem.Repository.UserRepository;
import com.example.HospitalManaSystem.dto.LoginRequestDto;
import com.example.HospitalManaSystem.dto.LoginResposeDto;
import com.example.HospitalManaSystem.dto.SignUpRequestDto;
import com.example.HospitalManaSystem.dto.SignUpResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepo patientRepo;

    public LoginResposeDto login(LoginRequestDto loginRequestDto) {


        //while login, request delegatee to authenticationManager for authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        //if user not valid, Spring Security throws an exception.
        //if user is valid
        //we will create JWT
        User user = (User) authentication.getPrincipal();  //manually typecast object to User
        String token = authUtil.generateAccessToken(user);
        return new LoginResposeDto(token, user.getId());

    }

//    public SignUpResponseDto signUp(LoginRequestDto signUpRequestDto) {
//        User user = userRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);
//
//        if (user != null) throw new IllegalArgumentException("user Already Exists");
//
//        //if not present
//        User newUser = new User();
//        user.setUsername(signUpRequestDto.getUsername());
//        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
//        userRepository.save(newUser);
//
//        return new SignUpResponseDto(user.getId(), user.getUsername());
//    }

    public User signUpInternal(SignUpRequestDto signUpRequestDto, AuthProviderType authProviderType, String providerId){
        User user = userRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);
        if(user != null) throw new IllegalArgumentException("User already exists");

        User newUser = new User();
        newUser.setUsername(signUpRequestDto.getUsername());
        newUser.setProviderId(providerId);
        newUser.setProviderType(authProviderType);
        newUser.setRoles(Set.of(RoleType.PATIENT));
        //whenever a user comes, it will treat as Patient

        if(authProviderType == AuthProviderType.EMAIL) {
            newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        }
        newUser = userRepository.save(newUser);

        //save patient while signUp as we assign patient role for each patient
        Patient patient = Patient.builder()
                .name(signUpRequestDto.getName())  //Not nullable field
                .user(newUser)  //PK
                .build();
        patientRepo.save(patient);
        return newUser;
    }

    //login Controller
    //as we get email from user when he/she will try to signUP
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        User user = signUpInternal(signUpRequestDto, AuthProviderType.EMAIL, null);
        return new SignUpResponseDto(user.getId(), user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResposeDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
//        fetch providerType and providerID;
        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationID(registrationId);
        String providerId = authUtil.getProviderIdFrom0Auth2User(oAuth2User, registrationId);

         //if user not present...we'll create it so orElse null
        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser==null){
            //signUp flow
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = signUpInternal(new SignUpRequestDto(username,null, name), providerType, providerId);
            //Now from this user we generate JWT
        }else if(user != null){  //found user with provider Id and type
            //and we found email in OAuth2 access token
            if(email != null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            //when emailUser!=null and user try to get access via Oauth2, we denied and specify exception
            throw new BadCredentialsException("This email is already registered with username and pswd");
        }

        //Give Access
        //JWT generate krne keliye User send krna pdega
        LoginResposeDto loginResposeDto = new LoginResposeDto(authUtil.generateAccessToken(user), user.getId());
        return ResponseEntity.ok(loginResposeDto);

    }
}

//    public ResponseEntity<LoginResposeDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId){
//        //fetch providerType and providerID;
//        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationID(registrationId);
//        String providerId = authUtil.getProviderIdFrom0Auth2User(oAuth2User, registrationId);
//
//        //if user not present...we'll create it so orElse null
//        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
//
//        //let say, user login from github and user give consent to share email, we found an email there
//        //we also save it in our DB so if user try to login with user, we don't need to create new acc.for same user
//        //we'll save user in username
//        //Business Logic
////        String email= oAuth2User.getAttribute("email");
////        User emailUser = userRepository.findByUsername(email).orElse(null);
//
//        if(user == null){
//            //signUp flow
////            String username = authUtil.determineUsernamefromOAuth2User();
//            user = signUpFromAuth2(new LoginRequestDto(username,null));
//        }//OAuth2
////        else if(user != null){
////            if(email != null && !email.isBlank() && !email.equals(user.getUsername())){
////                user.setUsername(email);
////                userRepository.save(user);
////            }
////        }else{
////            //user try to login via email let say but github already exists
////            throw new BadCredentialsException("This email is already registered with provider" + emailUser.getProviderType());
////        }
////
//        LoginResposeDto loginResponseDto = new LoginResposeDto(authUtil.generateAccessToken(user), user.getId());
//        return ResponseEntity.ok(loginResponseDto);
////    }

/*
✔ What is Authentication?

👉 It is an interface in Spring Security

✔ It represents:
Who the user is (principal)
Credentials (password)
Roles/authorities
Authentication status (true/false)

No, it does NOT return null if the user is invalid.

👉 Instead, Spring Security throws an exception.

🔄 Internal Delegation
AuthenticationManager
        ↓
AuthenticationProvider
        ↓
UserDetailsService
        ↓
PasswordEncoder
 */