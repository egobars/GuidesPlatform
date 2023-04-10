package edu.paper.guider.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "themes")
@Entity
public class Theme {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name="theme_id")
    private Long id;

    @Column(name = "theme_title", columnDefinition = "NVARCHAR(MAX)")
    private String title;
}
