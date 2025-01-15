package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Article;
import ma.xproce.backend.Dao.entities.ArticleStatus;

import ma.xproce.backend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller

public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/saveArticle")
    public String createArticle(
            Model model,
            @Valid Article article,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createArticle";
        }
        article.setSubmissionDate(LocalDateTime.now()); // Ajouter la date de soumission
        articleService.createArticle(article);
        return "redirect:/articles/indexPage";
    }

    @GetMapping("/detailsArticle")
    public String getArticleDetails(
            Model model,
            @RequestParam(name = "id") Long id) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        model.addAttribute("articleDetails", article);
        return "articleDetails";
    }

    @GetMapping("/deleteArticle")
    public String deleteArticle(@RequestParam(name = "id") Long id) {
        if (articleService.deleteArticle(id)) {
            return "redirect:/articles/indexPage";
        } else {
            return "error";
        }
    }

    @PostMapping("/updateArticle")
    public String updateArticleAction(
            Model model,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "abstractText") String abstractText,
            @RequestParam(name = "filePath") String filePath,
            @RequestParam(name = "status") String status) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        article.setTitle(title);
        article.setAbstractText(abstractText);
        article.setFilePath(filePath);
        article.setStatus(Enum.valueOf(ArticleStatus.class, status));
        articleService.updateArticle(article);
        return "redirect:/articles/indexPage";
    }

    @GetMapping("/indexPageArticle")
    public String listArticles(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "search", defaultValue = "") String keyword) {
        var articles = articleService.searchArticles(keyword, page, size);
        model.addAttribute("listArticles", articles.getContent());
        model.addAttribute("pages", new int[articles.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "indexArticles";
    }

    @GetMapping("/createArticle")
    public String createArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "createArticle";
    }

    @GetMapping("/editArticle")
    public String editArticleForm(
            Model model,
            @RequestParam(name = "id") Long id) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        model.addAttribute("articleToBeUpdated", article);
        return "editArticle";
    }
    @GetMapping("")
    public String hello() {
        return "layout";}

}
