package ma.xproce.backend.Dao.repositories;

import ma.xproce.backend.Dao.entities.Article;
import ma.xproce.backend.Dao.entities.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByConferenceId(Long conferenceId); // Trouver tous les articles pour une conférence donnée
    List<Article> findByStatus(ArticleStatus status); // Trouver les articles par statut
    List<Article> findByResearcherId(Long researcherId); // Trouver tous les articles soumis par un chercheur
    Page<Article> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);}
