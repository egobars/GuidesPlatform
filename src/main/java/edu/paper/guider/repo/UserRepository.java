package edu.paper.guider.repo;

import edu.paper.guider.model.Theme;
import edu.paper.guider.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
