package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Role;
import ma.xproce.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SpringBootApplication

public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public String listRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "roleList";
    }

    @GetMapping("/createRole")
    public String createRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "createRole";
    }
    @PostMapping("/createRole")
    public String ajouterRole(Model model, @Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", role); // Ajouter l'objet Role au modèle en cas d'erreur
            return "createRole";  // Retourner le formulaire avec les erreurs affichées
        }
        roleService.createRole(role); // Sauvegarder le rôle
        return "redirect:/roles"; // Rediriger vers la liste des rôles après succès
    }

    @PostMapping("/saveRole")
    public String saveRole(@Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createRole";
        }
        roleService.createRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/editRole")
    public String editRole(Model model, @RequestParam(name = "id") Long id) {
        Role role = roleService.getRoleById(id).orElse(null);
        if (role != null) {
            model.addAttribute("roleToBeUpdated", role);
            return "editRole";
        } else {
            return "error";
        }
    }

    @PostMapping("/updateRole")
    public String updateRole(@Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editRole";
        }
        roleService.updateRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/deleteRole")
    public String deleteRole(@RequestParam(name = "id") Long id, Model model) {
        try {
            if (roleService.deleteRole(id)) {
                return "redirect:/roles";
            } else {
                model.addAttribute("errorMessage", "Impossible de supprimer ce rôle.");
                return "roleList"; // On reste sur la page des rôles avec le message d'erreur
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ce rôle ne peut pas être supprimé car il est utilisé par des utilisateurs.");
            return "roleList"; // On reste sur la liste des rôles avec l'erreur affichée
        }
    }
}
