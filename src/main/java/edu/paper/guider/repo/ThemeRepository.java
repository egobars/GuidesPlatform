package edu.paper.guider.repo;

import edu.paper.guider.model.Guide;
import edu.paper.guider.model.Preview;
import edu.paper.guider.model.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Long> {
    Theme findThemeByTitle(String title);
}
