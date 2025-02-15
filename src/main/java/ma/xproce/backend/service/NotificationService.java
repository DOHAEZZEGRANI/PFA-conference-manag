package ma.xproce.backend.service;

import ma.xproce.backend.Dao.entities.Notification;
import ma.xproce.backend.Dao.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import ma.xproce.backend.Dao.entities.NotificationStatus;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Créer une nouvelle notification
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Obtenir toutes les notifications d'un utilisateur
    public List<Notification> getNotificationsByRecipient(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    // Obtenir toutes les notifications par statut
    public List<Notification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    // Mettre à jour une notification
    public Notification updateNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Supprimer une notification
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
