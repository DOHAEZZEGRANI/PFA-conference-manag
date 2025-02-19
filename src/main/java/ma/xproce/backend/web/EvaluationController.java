package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Evaluation;
import ma.xproce.backend.Dao.entities.User;
import ma.xproce.backend.service.ArticleService;
import ma.xproce.backend.service.EvaluationService;
import ma.xproce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SpringBootApplication
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @PostMapping("/saveEvaluation")
    public String ajouterEvaluation(Model model,
                                    @Valid Evaluation evaluation,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createEvaluation";
        }
        evaluationService.createEvaluation(evaluation);
        return "redirect:/evaluations";
    }

    @GetMapping("/evaluations")
    public String listEvaluations(Model model) {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        model.addAttribute("listEvaluations", evaluations);
        return "evaluations";
    }

    @GetMapping("/evaluationDetails")
    public String detailEvaluation(Model model,
                                   @RequestParam(name = "id") Long id) {
        Evaluation evaluation = evaluationService.getEvaluationById(id);
        model.addAttribute("EvaluationWithDetails", evaluation);
        return "evaluationDetails";
    }

    @GetMapping("/deleteEvaluation")
    public String deleteEvaluation(@RequestParam(name = "id") Long id) {
        if (evaluationService.deleteEvaluationById(id)) {
            return "redirect:/indexpage";
        } else {
            return "error";
        }
    }


}
