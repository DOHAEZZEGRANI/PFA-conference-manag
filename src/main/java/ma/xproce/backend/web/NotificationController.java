package ma.xproce.backend.web;

import ma.xproce.backend.Dao.entities.Notification;
import ma.xproce.backend.Dao.entities.NotificationStatus;
import ma.xproce.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Créer une nouvelle notification
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    // Obtenir toutes les notifications d'un utilisateur
    @GetMapping("/recipient/{recipientId}")
    public List<Notification> getNotificationsByRecipient(@PathVariable Long recipientId) {
        return notificationService.getNotificationsByRecipient(recipientId);
    }

    // Obtenir toutes les notifications par statut
    @GetMapping("/status/{status}")
    public List<Notification> getNotificationsByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatus(status);
    }

    // Mettre à jour une notification
    @PutMapping("/{id}")
    public Notification updateNotification(@PathVariable Long id, @RequestBody Notification notification) {
        notification.setId(id);
        return notificationService.updateNotification(notification);
    }

    // Supprimer une notification
    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
}
