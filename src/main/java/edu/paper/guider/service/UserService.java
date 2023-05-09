package edu.paper.guider.service;

import edu.paper.guider.dto.LoginForm;
import edu.paper.guider.dto.RegistrationForm;
import edu.paper.guider.model.Theme;
import edu.paper.guider.model.User;
import edu.paper.guider.repo.ThemeRepository;
import edu.paper.guider.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    UserRepository userRepository;
    ThemeRepository themeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.findByUsername(username) != null) {
            return userRepository.findByUsername(username);
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository repository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = repository;
    }

    public void addUser(RegistrationForm form) {
        User user = new User();

        user.setUsername(form.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
        user.setEmail(form.getEmail());

        userRepository.save(user);
    }

    public boolean checkUser(LoginForm form) {
        User user = userRepository.findByEmail(form.getEmail()).get(0);

        if (user == null) {
            return false;
        }
        return bCryptPasswordEncoder.matches(form.getPassword(), user.getPassword());
    }

    public boolean checkNames(String name) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public User getByName(String name) {
        return userRepository.findByUsername(name);
    }
}
