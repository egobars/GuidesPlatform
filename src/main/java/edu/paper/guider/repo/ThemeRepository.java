package edu.paper.guider.repo;

import edu.paper.guider.model.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Long> {
    Theme findThemeByTitle(String title);
}
