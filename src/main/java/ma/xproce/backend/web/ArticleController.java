package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.*;

import ma.xproce.backend.service.ArticleService;
import ma.xproce.backend.service.ConferenceService;
import ma.xproce.backend.service.EvaluationService;
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
import java.util.stream.Collectors;

@Controller
@SpringBootApplication
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
 @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private EvaluationService evaluationService;
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
        for (Article article : articles.getContent()) {
            long evaluationCount = evaluationService.getEvaluationCountByArticle(article);
            article.setEvaluationCount(evaluationCount); // Assuming a method to set this count
        }


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






        @GetMapping("/evaluateArticle")
        public String showEvaluationForm(Model model, @RequestParam(name = "id") Long id) {
            // Récupérer l'article
            Article article = articleService.getArticleById(id);
            if (article == null) {
                model.addAttribute("error", "Article introuvable.");
                return "evaluateArticle";
            }
            List<User> reviewer = userService.getAllUsers();


            // Log pour le débogage
            System.out.println("Nombre de reviewers trouvés : " + reviewer.size());
            for (User user : reviewer) {
                System.out.println("Reviewer: ID=" + user.getId() + ", Nom=" + user.getName()+ ", Nom=" + user.getRole().getName());
            }

            // Récupérer les reviewers
            List<User> reviewers = userService.getAllUsers().stream()
                    .filter(user -> "REVIEWER".equals(user.getRole().getName()))
                    .collect(Collectors.toList());

            // Log pour le débogage
            System.out.println("Nombre de reviewers trouvés : " + reviewers.size());
            for (User user : reviewers) {
                System.out.println("Reviewer: ID=" + user.getId() + ", Nom=" + user.getName());
            }

            // Gérer le cas où aucun reviewer n'est disponible
            if (reviewers.isEmpty()) {
                model.addAttribute("error", "Aucun évaluateur disponible.");
            }

            // Ajouter les attributs au modèle
            model.addAttribute("users", reviewers);
            model.addAttribute("articleId", article.getId());

            return "evaluations";  // Correspond au nom du fichier HTML
        }

        @PostMapping("/evaluateArticle")
        public String evaluateArticle(@RequestParam(name = "articleId") Long articleId,
                                      @RequestParam(name = "reviewerId", required = false) Long reviewerId,
                                      @RequestParam(name = "score") Float score,
                                      @RequestParam(name = "comments") String comments) {

            // Vérifier que l'article existe
            Article article = articleService.getArticleById(articleId);
            if (article == null) {
                return "error";  // Gérer le cas où l'article n'existe pas
            }

            // Vérifier que l'évaluateur existe et est bien un REVIEWER
            User reviewer = userService.getUserById(reviewerId).orElse(null);
            if (reviewer == null || !"REVIEWER".equals(reviewer.getRole().getName())) {
                return "error";  // Gérer le cas où l'évaluateur est invalide
            }

            // Créer une nouvelle évaluation
            Evaluation evaluation = new Evaluation();
            evaluation.setScore(score);
            evaluation.setComments(comments);
            evaluation.setReviewer(reviewer);
            evaluation.setArticle(article);

            // Sauvegarder l'évaluation
            evaluationService.createEvaluation(evaluation);

            return "redirect:/indexpage";
        }
    }


