package ma.xproce.backend.Dao.repositories;

import ma.xproce.backend.Dao.entities.Notification;
import ma.xproce.backend.Dao.entities.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Rechercher les notifications par ID du destinataire
    List<Notification> findByRecipientId(Long recipientId);

    // Rechercher les notifications par statut
    List<Notification> findByStatus(NotificationStatus status);
}
