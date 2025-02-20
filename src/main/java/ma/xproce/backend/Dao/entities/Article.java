package ma.xproce.backend.Dao.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String abstractText;

    private String filePath;

    private LocalDateTime submissionDate;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User researcher;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @OneToMany(mappedBy = "article")
    private Set<Evaluation> evaluations;
    private long evaluationCount;  // For storing the count temporarily

    // Getter and setter for evaluationCount
    public long getEvaluationCount() {
        return evaluationCount;
    }

    public void setEvaluationCount(long evaluationCount) {
        this.evaluationCount = evaluationCount;
    }

    // Getters and setters
}
