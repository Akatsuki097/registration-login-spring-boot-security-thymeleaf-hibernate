package net.javaguides.springboot.controller;



import net.javaguides.springboot.exception.UserNotFoundException;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

//    @PostMapping("/login")
//    public String afterLogin() {
//
//        return "login";
//    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model){
       User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "registration";
        }
        userService.save(user);
        return "redirect:/registration?success";
    }

//    @GetMapping("/users")
//    public String listRegisteredUsers(Model model){
//        List<User> users = userService.fetchUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }

//    @PostMapping("/users")
//    public User saveUser(@RequestBody User user){
//        return userService.save(user);
//    }

    @GetMapping("/users")
    public List<User> fetchUsers(){
        return userService.fetchUsers();
    }
//    public String fetchDepartments(){
//        return "AllProjects";
//    }


    @GetMapping("/users/{id}")
    public User fetchUserById( @PathVariable("id") Long userId) throws UserNotFoundException {
        return userService.fetchUserById(userId);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long userId){
        return userService.deleteUser(userId);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable("id") Long userId, @RequestBody User user){
        return userService.updateUser(userId, user);
    }
}
