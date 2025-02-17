package ma.xproce.backend.service;

import ma.xproce.backend.Dao.entities.Conference;
import ma.xproce.backend.Dao.entities.Role;
import ma.xproce.backend.Dao.repositories.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    public Conference createConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    public Page<Conference> searchConferences(String keyword, int page, int size) {
        return conferenceRepository.findByTitleContainingIgnoreCase(keyword, PageRequest.of(page, size));
    }

    public Conference getConferenceById(Long id) {
        Optional<Conference> optionalConference = conferenceRepository.findById(id);
        return optionalConference.orElse(null);
    }

    public Conference updateConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    public boolean deleteConferenceById(Long id) {
        if (conferenceRepository.existsById(id)) {
            conferenceRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }
}