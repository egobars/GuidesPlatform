package edu.paper.guider.repo;

import edu.paper.guider.model.Theme;
import edu.paper.guider.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByEmail(String email);
}
