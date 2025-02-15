package ma.xproce.backend.service;

import ma.xproce.backend.Dao.entities.Role;
import ma.xproce.backend.Dao.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Créer un nouveau rôle
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Récupérer un rôle par ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Récupérer un rôle par nom
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    // Récupérer tous les rôles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Mettre à jour un rôle
    public Role updateRole(Long id, Role role) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (existingRole.isPresent()) {
            Role updatedRole = existingRole.get();
            updatedRole.setName(role.getName());
            return roleRepository.save(updatedRole);
        }
        throw new RuntimeException("Role not found");
    }

    // Supprimer un rôle
    public void deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Role not found");
        }
    }
}
