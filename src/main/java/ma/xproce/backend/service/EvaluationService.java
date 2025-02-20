package ma.xproce.backend.service;

import ma.xproce.backend.Dao.entities.Article;
import ma.xproce.backend.Dao.entities.Evaluation;
import ma.xproce.backend.Dao.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public Evaluation createEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public Page<Evaluation> searchEvaluations(String keyword, int page, int size) {
        return evaluationRepository.findByCommentsContainingIgnoreCase(keyword, PageRequest.of(page, size));
    }
    public long getEvaluationCountByArticle(Article article) {
        return evaluationRepository.countByArticle(article);
    }


    public Evaluation getEvaluationById(Long id) {
        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(id);
        return optionalEvaluation.orElse(null);
    }

    public Evaluation updateEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public boolean deleteEvaluationById(Long id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }
}