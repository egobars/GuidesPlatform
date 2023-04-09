package edu.paper.guider.service;

import edu.paper.guider.dto.LoginForm;
import edu.paper.guider.dto.RegistrationForm;
import edu.paper.guider.model.User;
import edu.paper.guider.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    UserRepository userRepository;

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
        User user = userRepository.findByUsername(form.getUsername());

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
