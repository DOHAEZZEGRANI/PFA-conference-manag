package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Conference;
import ma.xproce.backend.Dao.entities.ConferenceStatus;
import ma.xproce.backend.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/conferences")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    // Créer une nouvelle conférence
    @PostMapping("/saveConference")
    public String createConference(
            Model model,
            @Valid Conference conference,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createConference"; // Retourne la vue avec erreurs
        }
        conference.setSubmissionStartDate(LocalDateTime.now()); // Exemple d'attribution de la date de soumission
        conferenceService.createConference(conference);
        return "redirect:/conferences/indexPage"; // Redirige vers la liste
    }

    // Obtenir une conférence par son ID
    @GetMapping("/details")
    public String getConferenceDetails(
            Model model,
            @RequestParam(name = "id") Long id) {
        Conference conference = conferenceService.getConferenceById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found"));
        model.addAttribute("conferenceDetails", conference);
        return "conferenceDetails"; // Vue de détails
    }

    // Supprimer une conférence
    @GetMapping("/delete")
    public String deleteConference(@RequestParam(name = "id") Long id) {
        if (conferenceService.deleteConference(id)) {
            return "redirect:/conferences/indexPage"; // Redirige vers la liste des conférences
        } else {
            return "error"; // Page d'erreur si la conférence n'a pas pu être supprimée
        }
    }

    // Mettre à jour une conférence
    @PostMapping("/updateConference")
    public String updateConferenceAction(
            Model model,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "submissionStartDate") String submissionStartDate, // Conversion possible de String en LocalDateTime
            @RequestParam(name = "submissionEndDate") String submissionEndDate,
            @RequestParam(name = "conferenceDate") String conferenceDate,
            @RequestParam(name = "status") String status) {
        Conference conference = conferenceService.getConferenceById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found"));

        // Conversion des dates depuis String
        conference.setTitle(title);
        conference.setDescription(description);
        conference.setSubmissionStartDate(LocalDateTime.parse(submissionStartDate));
        conference.setSubmissionEndDate(LocalDateTime.parse(submissionEndDate));
        conference.setConferenceDate(LocalDateTime.parse(conferenceDate));
        conference.setStatus(Enum.valueOf(ConferenceStatus.class, status));

        conferenceService.updateConference(conference);
        return "redirect:/conferences/indexPage"; // Redirige vers la liste des conférences
    }

    // Liste des conférences
    @GetMapping("/indexPage")
    public String listConferences(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "search", defaultValue = "") String keyword) {
        var conferences = conferenceService.searchConferences(keyword, page, size);
        model.addAttribute("listConferences", conferences.getContent());
        model.addAttribute("pages", new int[conferences.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "indexConferences"; // Vue de liste des conférences
    }

    // Formulaire de création de conférence
    @GetMapping("/createConference")
    public String createConferenceForm(Model model) {
        model.addAttribute("conference", new Conference());
        model.addAttribute("statusList", ConferenceStatus.values()); // Liste des statuts
        return "createConference"; // Vue de création
    }

    // Formulaire d'édition d'une conférence
    @GetMapping("/editConference")
    public String editConferenceForm(
            Model model,
            @RequestParam(name = "id") Long id) {
        Conference conference = conferenceService.getConferenceById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found"));
        model.addAttribute("conferenceToBeUpdated", conference);
        model.addAttribute("statusList", ConferenceStatus.values()); // Liste des statuts
        return "editConference"; // Formulaire d'édition
    }
}
