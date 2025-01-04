package ma.xproce.backend.service;

import ma.xproce.backend.Dao.entities.Conference;
import ma.xproce.backend.Dao.entities.ConferenceStatus;
import ma.xproce.backend.Dao.repositories.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    // Créer une nouvelle conférence
    public Conference createConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    // Obtenir une conférence par son ID
    public Optional<Conference> getConferenceById(Long id) {
        return conferenceRepository.findById(id);
    }

    // Mettre à jour une conférence existante
    public Conference updateConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    // Supprimer une conférence
    public boolean deleteConference(Long id) {
        if (conferenceRepository.existsById(id)) {
            conferenceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Obtenir une liste de conférences par statut
    public List<Conference> getConferencesByStatus(ConferenceStatus status) {
        return conferenceRepository.findByStatus(status);
    }

    // Obtenir une liste de conférences après une certaine date
    public List<Conference> getConferencesAfterDate(LocalDateTime date) {
        return conferenceRepository.findByConferenceDateAfter(date);
    }

    // Recherche de conférences avec pagination et mot-clé
    public Page<Conference> searchConferences(String keyword, int page, int size) {
        return conferenceRepository.findByTitleContainingIgnoreCase(keyword, PageRequest.of(page, size));
    }
}
