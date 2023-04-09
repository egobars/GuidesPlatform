package edu.paper.guider.controller;

import edu.paper.guider.dto.GuideForm;
import edu.paper.guider.model.Guide;
import edu.paper.guider.model.User;
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
@RequestMapping("/Guide")
public class GuideController {
    GuidesService guidesService;
    UserService userService;

    public GuideController(GuidesService guidesService, UserService userService) {
        this.guidesService = guidesService;
        this.userService = userService;
    }

    @GetMapping("/")
    @ApiOperation(value = "Guide by id")
    public Guide guideId(Long id) {
        return guidesService.searchById(id);
    }

    @GetMapping("/checker")
    @ApiOperation(value = "Checks guide by id")
    public void checker(Long id, List<String> preview, String text) {
        if (guidesService.check(id, preview, text)) {
            throw new ResponseStatusException(
                    HttpStatus.OK, "ok"
            );
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    @PostMapping("/create")
    @ApiOperation(value = "Creates guide")
    public void creator(@RequestBody GuideForm form) {
        guidesService.save(form);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "Creates guide")
    public void deleter(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByName(String.valueOf(auth.getPrincipal()));

        if (!guidesService.searchById(id).getUser().equals(user)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Not user's guide!"
            );
        } else {
            if (guidesService.deleteGuide(id)) {
                throw new ResponseStatusException(
                        HttpStatus.OK, "ok"
                );
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "No such guide!"
                );
            }
        }
    }
}
