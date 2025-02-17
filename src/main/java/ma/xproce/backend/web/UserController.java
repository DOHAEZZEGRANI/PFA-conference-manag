package ma.xproce.backend.web;

import jakarta.validation.Valid;
import ma.xproce.backend.Dao.entities.Role;
import ma.xproce.backend.Dao.entities.User;
import ma.xproce.backend.service.RoleService;
import ma.xproce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@SpringBootApplication
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/saveUser")
    public String saveUser(Model model, @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createUser";
        }
        userService.createUser(user);
        return "redirect:/userList";
    }
    @GetMapping("/createUser")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "createUser"; // Le nom du fichier Thymeleaf à afficher
    }

    @PostMapping("/createUser")
    public String ajouterUser(Model model, @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user); // Ajoutez l'objet au modèle
            model.addAttribute("roles", roleService.getAllRoles()); // Ajoutez les rôles au modèle
            return "createUser";  // Retourner le formulaire avec l'objet en cas d'erreur
        }
        userService.createUser(user); // Sauvegarder l'utilisateur
        return "redirect:/userList"; // Rediriger vers la page d'accueil ou une autre page après succès
    }





    @GetMapping("/userList")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "indexUsers";
    }

    @GetMapping("/editUser")
    public String editUser(Model model, @RequestParam(name = "id") Long id) {
        User user = userService.getUserById(id).orElse(null);
        if (user != null) {
            model.addAttribute("userToBeUpdated", user);
            model.addAttribute("roles", roleService.getAllRoles());
            return "editUser";
        } else {
            return "error";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editUser";
        }
        userService.updateUser(user);
        return "redirect:/userList";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        if (userService.deleteUser(id)) {
            return "redirect:/userList";
        } else {
            return "error";
        }
    }
}
