package ma.xproce.backend.service;

import ma.xproce.backend.Dao.entities.Role;
import ma.xproce.backend.Dao.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Créer un nouveau rôle
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Obtenir un rôle par son nom
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
