package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Article;
import ma.xproce.backend.Dao.entities.ArticleStatus;

import ma.xproce.backend.Dao.entities.Conference;
import ma.xproce.backend.Dao.entities.User;
import ma.xproce.backend.service.ArticleService;
import ma.xproce.backend.service.ConferenceService;
import ma.xproce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@SpringBootApplication
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
 @Autowired
    private ConferenceService conferenceService;

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
                                        @RequestParam(name = "filePath") String filePath,
                                        @RequestParam(name = "researcherId") Long researcherId,  // Assuming you're passing the researcher ID
                                        @RequestParam(name = "conferenceId") Long conferenceId) { // Assuming you're passing the conference ID
        // Retrieve the article from the database
        Article article = articleService.getArticleById(id);
        if (article != null) {
            // Set the article fields
            article.setTitle(title);
            article.setAbstractText(abstractText);
            article.setStatus(status);
            article.setSubmissionDate(LocalDateTime.now());
            article.setFilePath(filePath);
            article.setResearcher(userService.getUserById(researcherId).orElse(null));


            Conference conference = conferenceService.getConferenceById(conferenceId); // Assuming there's a method to get the conference by ID


            article.setConference(conference);

            // Update the article
            articleService.updateArticle(article);
            return "redirect:/indexpage";
        } else {
            return "error";
        }
    }
    @PostMapping("/affecter")
    public String modifierArticleAction(Model model,
                                        @RequestParam(name = "id") Long id,
                                        @RequestParam(name = "title") String title,

                                        @RequestParam(name = "researcherId") Long researcherId) // Assuming you're passing the researcher ID
                                         { // Assuming you're passing the conference ID
        // Retrieve the article from the database
        Article article = articleService.getArticleById(id);
        if (article != null) {
            // Set the article fields
            article.setTitle(title);

            article.setSubmissionDate(LocalDateTime.now());

            article.setResearcher(userService.getUserById(researcherId).orElse(null));




            // Update the article
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
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("conferences", conferenceService.getAllConferences());
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
        List<User> users = userService.getAllUsers(); // Fetch all users
        List<Conference> conferences = conferenceService.getAllConferences(); // Fetch all conferences

        if (article != null) {
            model.addAttribute("ArticleToBeUpdated", article);
            model.addAttribute("users", users);
            model.addAttribute("conferences", conferences);
            model.addAttribute("statuses", ArticleStatus.values());
            return "editArticle";
        } else {
            return "error";
        }
    }
    @GetMapping("/affecter")
    public String aff(Model model, @RequestParam(name = "id") Long id) {
        Article article = articleService.getArticleById(id);
        List<User> users = userService.getAllUsers(); // Fetch all users

        if (article != null) {
            model.addAttribute("ArticleToBeUpdated", article);
            model.addAttribute("users", users);


            return "affecter";
        } else {
            return "error";
        }
    }

}
