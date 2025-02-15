package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Article;
import ma.xproce.backend.Dao.entities.ArticleStatus;

import ma.xproce.backend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@SpringBootApplication
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/saveArticle")
    public String ajouterArticle(Model model,
                               @Valid Article article ,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createArticle";
        }
        articleService.createArticle(article);
        return "redirect:/indexpage";
    }


    @GetMapping("layout")
    public String acc() {
        return "redirect:/indexpage";
    }






    @GetMapping("/login")
    public String login() {
        return "login"; }
   @GetMapping("/")
    public String ia() {
        return "indexArticles"; }
    @GetMapping("/accueil")
    public String accueil() {
        return "accueil"; }




    @GetMapping("/detailsArticle")
    public String detailArticle(Model model,
                         @RequestParam(name = "id") Long id) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("ArticleWithDetails", article);
        return "/articleDetails";
    }



    @GetMapping("/delete")
    public String deleteArticletel(@RequestParam(name = "id") Long id) {
        if (articleService.deleteArticleById(id)) {
            return "redirect:/indexpage";
        } else {
            return "error";
        }
    }


    @PostMapping("/ajouterhArticle")
    public String modifierArticleAction(Model model,
                                      @RequestParam(name = "id") Long id,
                                      @RequestParam(name = "title") String title,
                                      @RequestParam(name = "status") ArticleStatus status,
                                      @RequestParam(name = "abstractText") String abstractText,


                                      @RequestParam(name = "filePath") String filePath) {
        Article article  = articleService.getArticleById(id);
        if (article != null) {
            article.setTitle(title);
            article.setAbstractText(abstractText);
            article.setStatus(status);
            article.setSubmissionDate(LocalDateTime.now());

            article.setFilePath(filePath);
            articleService.updateArticle(article);
            return "redirect:/indexpage";
        } else {
            return "error";
        }
    }





    @PostMapping("/ajouterOncee")
    public String ajouterArticleOnce(Model model, @Valid Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("Article", article); // Ajoutez l'objet au modèle
            return "/createArticle";  // Retourner le formulaire avec l'objet en cas d'erreur
        }
        articleService.createArticle(article);
        return "redirect:/indexpage";
    }

    @GetMapping("/createArticle")
    public String ajouterl(Model model) {
        model.addAttribute("Article", new Article());
        return "/createArticle";
    }



    @GetMapping("/indexpage")
    public String listProduit(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "taille", defaultValue = "6") int taille,
                              @RequestParam(name = "search", defaultValue = "") String keyword) {
        Page<Article> articles = articleService.searchArticles(keyword, page, taille);
        model.addAttribute("listArticle", articles.getContent());

        int[] pages = new int[articles.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "layout";
    }



    @GetMapping("/editArticle")
    public String editArticleAction(Model model, @RequestParam(name = "id") Long id) {
        Article article = articleService.getArticleById(id);
        if (article != null) {
            model.addAttribute("ArticleToBeUpdated", article);
            model.addAttribute("statuses", ArticleStatus.values());  // Ajouter les statuts disponibles au modèle
            return "editArticle";
        } else {
            return "error";
        }
    }
}
