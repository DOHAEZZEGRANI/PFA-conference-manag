package ma.xproce.backend.Dao.repositories;

import ma.xproce.backend.Dao.entities.Conference;
import ma.xproce.backend.Dao.entities.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    // Rechercher les évaluations par ID d'article
    List<Evaluation> findByArticleId(Long articleId);

    // Rechercher les évaluations par ID de reviewer
    List<Evaluation> findByReviewerId(Long reviewerId);
    Page<Evaluation> findByCommentsContainingIgnoreCase(String keyword, Pageable pageable);}
