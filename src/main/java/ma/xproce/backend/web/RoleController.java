package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Role;
import ma.xproce.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Créer un nouveau rôle
    @PostMapping("/saveRole")
    public String createRole(
            Model model,
            @Valid Role role,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createRole";
        }
        roleService.createRole(role);
        return "redirect:/roles";
    }

    // Afficher tous les rôles
    @GetMapping("/roles")
    public String listRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("listRoles", roles);
        return "rolesList";
    }

    // Modifier un rôle
    @PostMapping("/updateRole")
    public String updateRole(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "name") String name) {
        Role role = roleService.getRoleById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(name);
        roleService.createRole(role);
        return "redirect:/roles";
    }

    // Supprimer un rôle
    @GetMapping("/deleteRole")
    public String deleteRole(@RequestParam(name = "id") Long id) {
        roleService.deleteRole(id);
        return "redirect:/roles";
    }

    // Formulaire pour créer un rôle
    @GetMapping("/createRole")
    public String createRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "createRole";
    }

    // Formulaire pour modifier un rôle
    @GetMapping("/editRole")
    public String editRoleForm(
            Model model,
            @RequestParam(name = "id") Long id) {
        Role role = roleService.getRoleById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        model.addAttribute("roleToBeUpdated", role);
        return "editRole";
    }
}
