package edu.paper.guider.controller;

import edu.paper.guider.dto.CommentForm;
import edu.paper.guider.model.Comment;
import edu.paper.guider.model.Guide;
import edu.paper.guider.model.User;
import edu.paper.guider.service.CommentsService;
import edu.paper.guider.service.GuidesService;
import edu.paper.guider.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    CommentsService commentsService;

    UserService userService;

    public CommentController(CommentsService commentsService, UserService userService) {
        this.commentsService = commentsService;
        this.userService = userService;
    }

    @GetMapping("/guide")
    @ApiOperation(value = "Get comments by guide")
    public List<Comment> guideComments(Long id) {
        List<Comment> comments = commentsService.getCommentsByGuide(id);
        if (comments == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No such guide!"
            );
        }
        else {
            return comments;
        }
    }

    @GetMapping("/author")
    @ApiOperation(value = "Get comments by author")
    public List<Comment> authorComments(Long id) {
        List<Comment> comments = commentsService.getCommentsByUser(id);
        if (comments == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No such author!"
            );
        }
        else {
            return comments;
        }
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create comment")
    public void createComment(@RequestBody CommentForm form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByName(String.valueOf(auth.getPrincipal()));

        if (!commentsService.saveComment(form, user)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No such comment!"
            );
        } else {
            throw new ResponseStatusException(
                    HttpStatus.OK, "ok"
            );
        }
    }

    @PatchMapping("/create")
    @ApiOperation(value = "Patch comment")
    public void patchComment(@RequestBody CommentForm form, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByName(String.valueOf(auth.getPrincipal()));

        if (!commentsService.getCommentsById(id).getUser().equals(user)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Not user's comment!"
            );
        } else {
            if (commentsService.deleteComment(id)) {
                if (!commentsService.saveComment(form, user)) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "No such comment!"
                    );
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.OK, "ok"
                    );
                }
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "No such comment"
                );
            }
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete comment")
    public void deleteComment(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByName(String.valueOf(auth.getPrincipal()));

        if (!commentsService.getCommentsById(id).getUser().equals(user)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Not user's comment!"
            );
        } else {
            if (commentsService.deleteComment(id)) {
                throw new ResponseStatusException(
                        HttpStatus.OK, "ok"
                );
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "No such comment"
                );
            }
        }
    }
}
