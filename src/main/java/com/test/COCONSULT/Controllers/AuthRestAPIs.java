package com.test.COCONSULT.Controllers;


import com.test.COCONSULT.DTO.SignIn;
import com.test.COCONSULT.Entity.User;
import com.test.COCONSULT.JWT.JwtProvider;
import com.test.COCONSULT.JWT.JwtResponse;
import com.test.COCONSULT.Reposotories.RoleRepository;
import com.test.COCONSULT.Reposotories.UserRepository;
import com.test.COCONSULT.ServiceIMP.UserServiceIMP;
import com.test.COCONSULT.Services.MailSenderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserServiceIMP userServiceIMP;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MailSenderService mailSending;

    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody SignIn login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));

    }
    @RequestMapping(value = "/signup/employee/{roleName}", method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@Validated @RequestBody User user1,@PathVariable ("roleName")String roleName) {
       return userServiceIMP.registerUser(user1,  roleName);
    }

        @RequestMapping(value = "/signup/entreprise", method = RequestMethod.POST)
        public ResponseEntity<User> registerEntreprise(@Validated @RequestBody User user1){
          return userServiceIMP.registerEntreprise(user1);
    }
    @RequestMapping(value = "/signupadmin", method = RequestMethod.POST)
    public ResponseEntity<User> registerAdmin(@Valid @RequestBody User user)  {
        return userServiceIMP.registerAdmin(user);
    }
}
















