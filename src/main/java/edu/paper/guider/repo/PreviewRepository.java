package edu.paper.guider.repo;

import edu.paper.guider.model.Preview;
import edu.paper.guider.model.Theme;
import org.springframework.data.repository.CrudRepository;

public interface PreviewRepository extends CrudRepository<Preview, Long> {
    Preview findPreviewByImage(String image);
}
