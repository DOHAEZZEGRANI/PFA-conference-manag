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
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime submissionStartDate;

    private LocalDateTime submissionEndDate;

    private LocalDateTime conferenceDate;

    @Enumerated(EnumType.STRING)
    private ConferenceStatus status;

    @ElementCollection
    private Set<String> topics;

    @OneToMany(mappedBy = "conference")
    private Set<Article> articles;


    // Getters and setters
}