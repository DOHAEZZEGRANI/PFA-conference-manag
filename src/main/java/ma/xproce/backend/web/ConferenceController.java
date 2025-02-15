package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Conference;
import ma.xproce.backend.service.ConferenceService;
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
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    // Sauvegarder une conférence
    @PostMapping("/saveConference")
    public String saveConference(Model model,
                                 @Valid Conference conference,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createConference";  // Afficher le formulaire avec erreurs
        }
        conferenceService.createConference(conference);
        return "redirect:/conferenceIndex";  // Redirection vers la page des conférences
    }

    // Rediriger vers la page d'index des conférences

    // Afficher les détails d'une conférence
    @GetMapping("/conferenceDetails")
    public String conferenceDetails(Model model,
                                    @RequestParam(name = "id") Long id) {
        Conference conference = conferenceService.getConferenceById(id);
        if (conference != null) {
            model.addAttribute("ConferenceWithDetails", conference);
            return "/conferenceDetails";  // Afficher les détails de la conférence
        } else {
            return "error";  // Afficher une erreur si la conférence n'existe pas
        }
    }

    // Supprimer une conférence
    @GetMapping("/deleteConference")
    public String deleteConference(@RequestParam(name = "id") Long id) {
        if (conferenceService.deleteConferenceById(id)) {
            return "redirect:/conferenceIndex";  // Rediriger après suppression
        } else {
            return "error";  // Afficher une erreur si la suppression échoue
        }
    }

    // Mettre à jour une conférence
    @PostMapping("/updateConference")
    public String updateConference(Model model,
                                   @RequestParam(name = "id") Long id,
                                   @RequestParam(name = "title") String title,
                                   @RequestParam(name = "description") String description,
                                   @RequestParam(name = "submissionStartDate") LocalDateTime submissionStartDate,
                                   @RequestParam(name = "submissionEndDate") LocalDateTime submissionEndDate,
                                   @RequestParam(name = "conferenceDate") LocalDateTime conferenceDate) {
        Conference conference = conferenceService.getConferenceById(id);
        if (conference != null) {
            conference.setTitle(title);
            conference.setDescription(description);
            conference.setSubmissionStartDate(submissionStartDate);
            conference.setSubmissionEndDate(submissionEndDate);
            conference.setConferenceDate(conferenceDate);

            conferenceService.updateConference(conference);
            return "redirect:/conferenceIndex";  // Rediriger après mise à jour
        } else {
            return "error";  // Afficher une erreur si la conférence n'existe pas
        }
    }

    // Formulaire pour créer une conférence
    @GetMapping("/createConference")
    public String createConferenceForm(Model model) {
        model.addAttribute("Conference", new Conference());  // Créer une nouvelle conférence
        return "createConference";  // Afficher le formulaire de création
    }

    // Liste des conférences avec pagination et recherche
    @GetMapping("/conferenceIndex")
    public String listConferences(Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "6") int size,
                                  @RequestParam(name = "search", defaultValue = "") String keyword) {
        Page<Conference> conferences = conferenceService.searchConferences(keyword, page, size);
        model.addAttribute("listConference", conferences.getContent());

        int[] pages = new int[conferences.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "indexConferences";  // Afficher la liste des conférences
    }

    // Formulaire pour modifier une conférence
    @GetMapping("/editConference")
    public String editConferenceForm(Model model, @RequestParam(name = "id") Long id) {
        Conference conference = conferenceService.getConferenceById(id);
        if (conference != null) {
            model.addAttribute("ConferenceToBeUpdated", conference);  // Ajouter la conférence à modifier au modèle
            return "editConference";  // Afficher le formulaire d'édition
        } else {
            return "error";  // Afficher une erreur si la conférence n'existe pas
        }
    }
}
