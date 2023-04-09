package edu.paper.guider.repo;

import edu.paper.guider.model.Comment;
import edu.paper.guider.model.Guide;
import edu.paper.guider.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends CrudRepository<Comment, Long> {
    List<Comment> findCommentsByGuide(Guide guide);
    List<Comment> findCommentsByUser(User user);
    Comment findCommentById(Long id);
}
