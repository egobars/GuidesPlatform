package edu.paper.guider.repo;

import edu.paper.guider.model.Comment;
import edu.paper.guider.model.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    List<Vote> findAllByComment(Comment comment);
}
