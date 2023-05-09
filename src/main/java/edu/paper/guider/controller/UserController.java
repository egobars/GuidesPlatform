package edu.paper.guider.controller;

import edu.paper.guider.dto.LoginForm;
import edu.paper.guider.dto.RegistrationForm;
import edu.paper.guider.model.User;
import edu.paper.guider.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {
    AuthenticationManager authenticationManager;
    UserService userService;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/reg")
    @ApiOperation(value = "Registration for new user")
    public void auth(@RequestBody RegistrationForm form, HttpServletRequest request) {
        if (!userService.checkNames(form.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Name taken already!"
            );
        }
        userService.addUser(form);
        this.authenticateUserAndSetSession(form.getUsername(), form.getPassword(), request);
    }

    @PostMapping("/out")
    @ApiOperation(value = "Logging out")
    public void auth(HttpServletRequest request) throws ServletException {
        request.logout();
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @PostMapping("/log")
    @ApiOperation(value = "Logging in")
    public void login(@RequestBody LoginForm form, HttpServletRequest request) {
        if (!userService.checkUser(form)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bad credentials!"
            );
        } else {
            this.authenticateUserAndSetSession(form.getEmail(), form.getPassword(), request);
        }
    }

    @GetMapping("/user")
    @ApiOperation(value = "Getting current user")
    public Object user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }

    @PostMapping("/favs")
    @ApiOperation(value = "Adding favourite themes")
    public void fav() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current = (User)auth.getPrincipal();
    }

    private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
