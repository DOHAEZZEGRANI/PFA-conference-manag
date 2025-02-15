package ma.xproce.backend.service;

import ma.xproce.backend.Dao.entities.Article;
import ma.xproce.backend.Dao.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    public Page<Article> searchArticles(String keyword, int page, int size) {
        return articleRepository.findByTitleContainingIgnoreCase(keyword, PageRequest.of(page, size));
    }


    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> getArticlesByConference(Long conferenceId) {
        return articleRepository.findByConferenceId(conferenceId);
    }

    public List<Article> getArticlesByResearcher(Long researcherId) {
        return articleRepository.findByResearcherId(researcherId);
    }

    public Article getArticleById(Long id) {
        try {
            return articleRepository.findById(id).get();

        }catch (Exception e){

            System.out.println(e.getMessage());
            return null;
        }
    }
    public Article updateArticle(Article article) {
        return articleRepository.save(article);
    }

    public boolean deleteArticle(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean deleteArticle(Article article) {
        try {
            articleRepository.delete(article);
            return true;

        }catch (Exception e){

            System.out.println(e.getMessage());

            return false;
        }
    }



    public Boolean deleteArticleById(Long id) {
        try {

            articleRepository.deleteById(id);
            return true;

        }catch (Exception e){

            System.out.println(e.getMessage());

            return false;
        }
    }
}