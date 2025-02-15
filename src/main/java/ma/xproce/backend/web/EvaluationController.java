package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Evaluation;
import ma.xproce.backend.Dao.entities.EvaluationDecision;
import ma.xproce.backend.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    // Créer une nouvelle évaluation
    @PostMapping("/saveEvaluation")
    public String createEvaluation(
            Model model,
            @Valid Evaluation evaluation,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createEvaluation";
        }
        evaluationService.createEvaluation(evaluation);
        return "redirect:/evaluations?articleId=" + evaluation.getArticle().getId();
    }

    // Afficher toutes les évaluations pour un article
    @GetMapping("/evaluations")
    public String getEvaluationsByArticle(
            Model model,
            @RequestParam(name = "articleId") Long articleId) {
        var evaluations = evaluationService.getEvaluationsByArticle(articleId);
        model.addAttribute("listEvaluations", evaluations);
        model.addAttribute("articleId", articleId);
        return "evaluationsList";
    }

    // Formulaire pour créer une évaluation
    @GetMapping("/createEvaluation")
    public String createEvaluationForm(
            Model model,
            @RequestParam(name = "articleId") Long articleId) {
        Evaluation evaluation = new Evaluation();
        evaluation.setArticle(new ma.xproce.backend.Dao.entities.Article());
        evaluation.getArticle().setId(articleId);
        model.addAttribute("evaluation", evaluation);
        model.addAttribute("decisions", EvaluationDecision.values());
        return "createEvaluation";
    }

    // Mettre à jour une évaluation
    @PostMapping("/updateEvaluation")
    public String updateEvaluation(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "score") Float score,
            @RequestParam(name = "comments") String comments,
            @RequestParam(name = "decision") String decision) {
        Evaluation evaluation = evaluationService.updateEvaluation(
                evaluationService.getEvaluationsByArticle(id).stream()
                        .filter(e -> e.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Evaluation not found"))
        );
        evaluation.setScore(score);
        evaluation.setComments(comments);
        evaluation.setDecision(EvaluationDecision.valueOf(decision));
        evaluationService.updateEvaluation(evaluation);
        return "redirect:/evaluations?articleId=" + evaluation.getArticle().getId();
    }

    // Supprimer une évaluation
    @GetMapping("/deleteEvaluation")
    public String deleteEvaluation(@RequestParam(name = "id") Long id) {
        Evaluation evaluation = evaluationService.getEvaluationsByArticle(id)
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));
        evaluationService.deleteEvaluation(id);
        return "redirect:/evaluations?articleId=" + evaluation.getArticle().getId();
    }
}
