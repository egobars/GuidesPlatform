package edu.paper.guider.service;

import edu.paper.guider.dto.GuideForm;
import edu.paper.guider.model.*;
import edu.paper.guider.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GuidesService {
    EmailService emailService;
    PreviewRepository previewRepository;
    CommentsRepository commentsRepository;
    ThemeRepository themeRepository;
    GuidesRepository guidesRepository;
    UserRepository userRepository;

    public GuidesService(ThemeRepository themeRepository, GuidesRepository guidesRepository, PreviewRepository previewRepository, UserRepository userRepository, CommentsRepository commentsRepository, EmailService service) {
        this.themeRepository = themeRepository;
        this.guidesRepository = guidesRepository;
        this.previewRepository = previewRepository;
        this.userRepository = userRepository;
        this.commentsRepository = commentsRepository;
        this.emailService = service;
    }

    public List<Guide> getAllGuides() {
        return StreamSupport.stream(guidesRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Guide> searchByTheme(List<String> theme) {
        List<Guide> guides = new ArrayList<>();
        List<Guide> allGuides = this.getAllGuides();

        for (String name : theme) {
            for (Guide guide : allGuides) {
                for (Theme themer : themeRepository.findAllByTitle(name)) {
                    if (guide.getTheme().contains(themer)) {
                        guides.add(guide);
                    }
                }
            }
        }
        return guides;
    }

    public List<Guide> searchByTheme(String theme) {
        List<Guide> guides = new ArrayList<>();
        List<Guide> allGuides = this.getAllGuides();

        for (Guide guide : allGuides) {
            for (Theme themer : themeRepository.findAllByTitle(theme)) {
                if (guide.getTheme().contains(themer)) {
                    guides.add(guide);
                }
            }
        }
        return guides;
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
            Preview preview = new Preview();
            preview.setImage(str);

            previewRepository.save(preview);
            prev.add(preview);
        }
        guide.setPreview(prev);

        Set<Theme> theme = new HashSet<>();
        for (String str : form.getTheme()) {
            Theme themer = new Theme();
            themer.setTitle(str);

            themeRepository.save(themer);

            theme.add(themer);
        }
        guide.setTheme(theme);

        User user = userRepository.findByUsername(form.getUser().getUsername());

        if (user == null) {
            user = new User();

            user.setUsername(form.getUser().getUsername());
            userRepository.save(user);
        }

        guide.setUser(user);
        guidesRepository.save(guide);

        sendEmail(form.getTheme());
    }

    private void sendEmail(List<String> themes) {
        for (User user : userRepository.findAll()) {
            emailService.sendEmail("burnyshevni@gmail.com", "New Guide!", "There are some new guides out there!");
        }
    }
}
