package ma.xproce.backend.web;

import ma.xproce.backend.Dao.entities.Evaluation;
import ma.xproce.backend.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    // Créer une nouvelle évaluation
    @PostMapping
    public Evaluation createEvaluation(@RequestBody Evaluation evaluation) {
        return evaluationService.createEvaluation(evaluation);
    }

    // Obtenir toutes les évaluations d'un article
    @GetMapping("/article/{articleId}")
    public List<Evaluation> getEvaluationsByArticle(@PathVariable Long articleId) {
        return evaluationService.getEvaluationsByArticle(articleId);
    }

    // Obtenir toutes les évaluations faites par un reviewer
    @GetMapping("/reviewer/{reviewerId}")
    public List<Evaluation> getEvaluationsByReviewer(@PathVariable Long reviewerId) {
        return evaluationService.getEvaluationsByReviewer(reviewerId);
    }

    // Mettre à jour une évaluation
    @PutMapping("/{id}")
    public Evaluation updateEvaluation(@PathVariable Long id, @RequestBody Evaluation evaluation) {
        evaluation.setId(id);
        return evaluationService.updateEvaluation(evaluation);
    }

    // Supprimer une évaluation
    @DeleteMapping("/{id}")
    public void deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
    }
}
