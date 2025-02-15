package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Notification;
import ma.xproce.backend.Dao.entities.NotificationStatus;
import ma.xproce.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    // Créer une nouvelle notification
    @GetMapping("/createNotification")
    public String createNotificationForm(Model model) {
        model.addAttribute("notification", new Notification()); // Initialize an empty Notification object
        return "createNotification";  // This refers to createNotification.html
    }

    // Save notification
    @PostMapping("/saveNotification")
    public String createNotification(@Valid Notification notification, BindingResult result) {
        if (result.hasErrors()) {
            return "createNotification";  // Return to the form if there are validation errors
        }
        notificationService.saveNotification(notification);  // Save logic in the service
        return "redirect:/notifications";  // Redirect to the notifications list
    }

    // Afficher toutes les notifications d'un utilisateur
    @GetMapping("/notifications")
    public String getNotificationsByRecipient(
            Model model,
            @RequestParam(name = "recipientId") Long recipientId) {
        List<Notification> notifications = notificationService.getNotificationsByRecipient(recipientId);
        model.addAttribute("listNotifications", notifications);
        model.addAttribute("recipientId", recipientId);
        return "notificationsList";
    }

    // Marquer une notification comme lue
    @GetMapping("/markNotificationAsRead")
    public String markNotificationAsRead(
            @RequestParam(name = "id") Long id) {
        Notification notification = notificationService.getNotificationsByRecipient(id)
                .stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(NotificationStatus.READ);
        notificationService.updateNotification(notification);
        return "redirect:/notifications?recipientId=" + notification.getRecipient().getId();
    }

    // Supprimer une notification
    @GetMapping("/deleteNotification")
    public String deleteNotification(@RequestParam(name = "id") Long id) {
        Notification notification = notificationService.getNotificationsByRecipient(id)
                .stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notificationService.deleteNotification(id);
        return "redirect:/notifications?recipientId=" + notification.getRecipient().getId();
    }

    // Formulaire pour créer une notification

}
