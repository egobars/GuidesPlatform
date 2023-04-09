package edu.paper.guider.repo;

import com.sun.istack.NotNull;
import edu.paper.guider.model.Guide;
import edu.paper.guider.model.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GuidesRepository extends CrudRepository<Guide, Long> {
    List<Guide> findAllByThemeIn(Set<Theme> theme);

    Optional<Guide> findById(Long id);
}
