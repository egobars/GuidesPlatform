package edu.paper.guider.service;

import edu.paper.guider.dto.CommentForm;
import edu.paper.guider.model.Comment;
import edu.paper.guider.model.Guide;
import edu.paper.guider.model.User;
import edu.paper.guider.model.Vote;
import edu.paper.guider.repo.CommentsRepository;
import edu.paper.guider.repo.GuidesRepository;
import edu.paper.guider.repo.UserRepository;
import edu.paper.guider.repo.VoteRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    CommentsRepository commentsRepository;
    GuidesRepository guidesRepository;
    UserRepository userRepository;
    VoteRepository voteRepository;

    public CommentsService(CommentsRepository commentsRepository, GuidesRepository guidesRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.commentsRepository = commentsRepository;
        this.guidesRepository = guidesRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
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

    public boolean saveComment(CommentForm form/*, User user*/) {
        Comment comment = new Comment();

        // comment.setUser(user);
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

    public Long getScore(Comment comment) {
        List<Vote> votes = voteRepository.findAllByComment(comment);

        long counter = 0;
        for (Vote vote : votes) {
            if (vote.isUpvote()) {
                ++counter;
            } else {
                --counter;
            }
        }
        return counter;
    }

    public boolean vote(Long id, boolean upvote) {
        Comment comment = getCommentsById(id);

        if (comment == null) {
            return false;
        }

        Vote vote = new Vote();
        vote.setUpvote(upvote);
        vote.setComment(comment);

        voteRepository.save(vote);

        return true;
    }
}
