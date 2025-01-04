package ma.xproce.backend.Dao.repositories;

import ma.xproce.backend.Dao.entities.Article;
import ma.xproce.backend.Dao.entities.Conference;
import ma.xproce.backend.Dao.entities.ConferenceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    List<Conference> findByStatus(ConferenceStatus status); // Trouver les conférences par statut
    List<Conference> findByConferenceDateAfter(LocalDateTime date); // Trouver les conférences à venir
   // Trouver tous les articles soumis par un chercheur
    Page<Conference> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);}

