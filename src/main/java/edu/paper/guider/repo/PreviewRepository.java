package edu.paper.guider.repo;

import edu.paper.guider.model.Guide;
import edu.paper.guider.model.Preview;
import edu.paper.guider.model.Theme;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreviewRepository extends CrudRepository<Preview, Long> {
    Preview findPreviewByImage(String image);
}
