package com.info.country.controller;

import com.info.country.config.secuirty.JwtTokenUtil;
import com.info.country.config.secuirty.UserDetailsServiceImpl;
import com.info.country.constant.AppConstants;
import com.info.country.model.User;
import com.info.country.service.UserService;
import com.info.country.service.dto.responses.JwtRequest;
import com.info.country.service.dto.responses.JwtResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/" + AppConstants.API_VERSION + "/users")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    @ApiOperation(value = "Login to access resources")
    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        Optional<User> user = userService.findByUserName(authenticationRequest.getUsername());
        user.get().setPassword(null);
        log.info("Login successful.");
        return ResponseEntity.ok(new JwtResponse(user.get(), token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("Login successful.");
        } catch (DisabledException e) {
            log.error("USER_DISABLED.");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.error("INVALID_CREDENTIALS.");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        log.info("Logout successful.");
        return ResponseEntity.ok(AppConstants.LOGOUT_SUCCESS);
    }
}
