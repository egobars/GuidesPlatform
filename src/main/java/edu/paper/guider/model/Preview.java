package edu.paper.guider.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "previews")
@Entity
public class Preview {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name="preview_id")
    private Long id;

    @Column(name = "image")
    private String image;
}
