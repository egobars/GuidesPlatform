package edu.paper.guider.dto;

import edu.paper.guider.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideForm {
    private String title;
    private List<String> preview;
    private List<String> theme;
    private String text;
    private UserForm user;
}
