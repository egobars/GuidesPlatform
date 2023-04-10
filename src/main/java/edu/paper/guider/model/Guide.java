package edu.paper.guider.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guide")
@Entity
public class Guide {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name="guide_id")
    private Long id;

    @Column(name = "title", length = 100000)
    private String title;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany
    private Set<Preview> preview;

    @OneToMany
    private Set<Theme> theme;

    @Column(name = "text", length = 100000)
    private String text;
}
