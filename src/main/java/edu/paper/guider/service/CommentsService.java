package edu.paper.guider.service;

import edu.paper.guider.dto.CommentForm;
import edu.paper.guider.model.Comment;
import edu.paper.guider.model.Guide;
import edu.paper.guider.model.User;
import edu.paper.guider.repo.CommentsRepository;
import edu.paper.guider.repo.GuidesRepository;
import edu.paper.guider.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    CommentsRepository commentsRepository;
    GuidesRepository guidesRepository;
    UserRepository userRepository;

    public CommentsService(CommentsRepository commentsRepository, GuidesRepository guidesRepository, UserRepository userRepository) {
        this.commentsRepository = commentsRepository;
        this.guidesRepository = guidesRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getCommentsByGuide(Long id) {
        Guide guide;
        if (guidesRepository.findById(id).isPresent()) {
            guide = guidesRepository.findById(id).get();
        } else {
            return null;
        }

        return commentsRepository.findCommentsByGuide(guide);
    }

    public List<Comment> getCommentsByUser(Long id) {
        User user;
        if (userRepository.findById(id).isPresent()) {
            user = userRepository.findById(id).get();
        } else {
            return null;
        }

        return commentsRepository.findCommentsByUser(user);
    }

    public Comment getCommentsById(Long id) {
        return commentsRepository.findCommentById(id);
    }

    public boolean saveComment(CommentForm form, User user) {
        Comment comment = new Comment();

        comment.setUser(user);
        comment.setText(form.getText());
        if (guidesRepository.findById(form.getId()).isPresent()) {
            comment.setGuide(guidesRepository.findById(form.getId()).get());
        } else {
            return false;
        }

        commentsRepository.save(comment);
        return true;
    }

    public boolean deleteComment(Long id) {
        if (guidesRepository.findById(id).isPresent()) {
            commentsRepository.delete(commentsRepository.findCommentById(id));
        } else {
            return false;
        }
        return true;
    }
}
