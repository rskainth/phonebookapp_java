package phoneBook.RestService;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import phoneBook.Contracts.BusinessLogic.IUserBL;
import phoneBook.Contracts.BusinessObjects.IUser;
import phoneBook.Contracts.DataAccess.IUserDA;
import phoneBook.RestService.Security.CustomUser;
import phoneBook.RestService.Security.OAuthAuthenticationResponse;
import phoneBook.RestService.Security.OAuthTokenProvider;
import phoneBook.RestService.Security.LoginRequest;
import phoneBook.RestService.Security.SignUpRequest;
import phoneBook.businessObjects.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    IUserDA dataAccess;
	
	@Autowired
	IUserBL businessLogic;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OAuthTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        CustomUser user = (CustomUser)authentication.getPrincipal();
        
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new OAuthAuthenticationResponse(jwt, user));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        
    	// Creating user's account
        IUser user = new User();
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());
        user.setName(signUpRequest.getName());

        user = businessLogic.Add(user);

        URI location = URI.create("");

		return ResponseEntity.created(location).body(true);
    }
}