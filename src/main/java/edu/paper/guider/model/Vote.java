package edu.paper.guider.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "votes")
@Entity
public class Vote {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name="vote_id")
    private Long id;

    @Column(name = "upvote")
    private boolean upvote;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
