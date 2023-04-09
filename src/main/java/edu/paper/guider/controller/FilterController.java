package edu.paper.guider.controller;

import edu.paper.guider.model.Guide;
import edu.paper.guider.service.GuidesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilterController {
    GuidesService guidesService;

    public FilterController(GuidesService guidesService) {
        this.guidesService = guidesService;
    }

    @GetMapping("/GuidesTheme")
    @ApiOperation(value = "Guides by certain theme")
    public List<Guide> themedGuides(String theme) {
        return guidesService.searchByTheme(theme);
    }

    @GetMapping("/GuidesThemes")
    @ApiOperation(value = "Guides by several themes")
    public List<Guide> themedGuides(List<String> theme) {
        return guidesService.searchByTheme(theme);
    }

    @GetMapping("/Guides")
    @ApiOperation(value = "All guides")
    public List<Guide> allGuides() {
        return guidesService.getAllGuides();
    }
}
