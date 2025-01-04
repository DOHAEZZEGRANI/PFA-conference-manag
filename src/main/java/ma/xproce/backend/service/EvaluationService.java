package ma.xproce.backend.service;

import ma.xproce.backend.Dao.entities.Evaluation;
import ma.xproce.backend.Dao.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    // Créer une nouvelle évaluation
    public Evaluation createEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    // Obtenir toutes les évaluations d'un article
    public List<Evaluation> getEvaluationsByArticle(Long articleId) {
        return evaluationRepository.findByArticleId(articleId);
    }

    // Obtenir toutes les évaluations faites par un reviewer
    public List<Evaluation> getEvaluationsByReviewer(Long reviewerId) {
        return evaluationRepository.findByReviewerId(reviewerId);
    }

    // Mettre à jour une évaluation
    public Evaluation updateEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    // Supprimer une évaluation
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
}
