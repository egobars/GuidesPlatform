package edu.paper.guider.service;

import edu.paper.guider.dto.GuideForm;
import edu.paper.guider.model.*;
import edu.paper.guider.repo.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GuidesService {
    PreviewRepository previewRepository;
    CommentsRepository commentsRepository;
    ThemeRepository themeRepository;
    GuidesRepository guidesRepository;
    UserRepository userRepository;

    public GuidesService(ThemeRepository themeRepository, GuidesRepository guidesRepository, PreviewRepository previewRepository, UserRepository userRepository, CommentsRepository commentsRepository) {
        this.themeRepository = themeRepository;
        this.guidesRepository = guidesRepository;
        this.previewRepository = previewRepository;
        this.userRepository = userRepository;
        this.commentsRepository = commentsRepository;
    }

    public List<Guide> getAllGuides() {
        return StreamSupport.stream(guidesRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Guide> searchByTheme(List<String> theme) {
        Set<Theme> themes = new HashSet<>();

        for (String name : theme) {
            themes.add(themeRepository.findThemeByTitle(name));
        }
        return guidesRepository.findAllByThemeIn(themes);
    }

    public List<Guide> searchByTheme(String theme) {
        Set<Theme> themes = new HashSet<>();
        themes.add(themeRepository.findThemeByTitle(theme));

        return guidesRepository.findAllByThemeIn(themes);
    }

    public Guide searchById(Long id) {
        if (guidesRepository.findById(id).isPresent()) {
            return guidesRepository.findById(id).get();
        }
        return null;
    }

    public boolean check(Long id, List<String> preview, String text) {
        Guide guide = searchById(id);

        if (preview.size() == guide.getPreview().size()) {
            for (Preview name : guide.getPreview()) {
                if (!preview.contains(name.getImage())) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return guide.getText().equals(text);
    }

    public boolean deleteGuide(Long id) {
        Guide guide = searchById(id);

        if (guide == null) {
            return false;
        } else {
            guidesRepository.delete(guide);
        }

        List<Comment> comments = commentsRepository.findCommentsByGuide(guide);
        commentsRepository.deleteAll(comments);

        return true;
    }

    public void save(GuideForm form) {
        Guide guide = new Guide();

        guide.setTitle(form.getTitle());
        guide.setText(form.getText());

        Set<Preview> prev = new HashSet<>();
        for (String str : form.getPreview()) {
            Preview preview = previewRepository.findPreviewByImage(str);
            if (preview == null) {
                preview = new Preview();

                preview.setImage(str);

                previewRepository.save(preview);
            }
            prev.add(preview);
        }
        guide.setPreview(prev);

        Set<Theme> theme = new HashSet<>();
        for (String str : form.getTheme()) {
            Theme themer = themeRepository.findThemeByTitle(str);
            if (themer == null) {
                themer = new Theme();

                themer.setTitle(str);

                themeRepository.save(themer);
            }
            theme.add(themer);
        }
        guide.setTheme(theme);

        User user = userRepository.findByUsername(form.getUser().getUsername());

        if (userRepository.findByUsername(form.getUser().getUsername()) == null) {
            user = new User();

            user.setUsername(form.getUser().getUsername());
            userRepository.save(user);

            guide.setUser(user);
        }

        guide.setUser(user);
        System.out.println(guide);
        guidesRepository.save(guide);
    }
}
