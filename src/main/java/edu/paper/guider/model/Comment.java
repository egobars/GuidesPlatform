package edu.paper.guider.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Entity
public class Comment {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name="comment_id")
    private Long id;

    @Column(name = "text", columnDefinition = "NVARCHAR(MAX)")
    private String text;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
