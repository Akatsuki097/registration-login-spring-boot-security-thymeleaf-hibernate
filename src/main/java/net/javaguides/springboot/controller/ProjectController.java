package net.javaguides.springboot.controller;



import net.javaguides.springboot.exception.ProjectNotFoundException;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.ProjectService;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;


    @PostMapping("/saveproject")
    public String saveProject(@ModelAttribute("project") @Valid Project project , Model model){
        System.out.println("ggs");

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(loggedInUser.getName());
//        project.setProjectOwner(loggedInUser);
//       System.out.println(" project"+ user);
        project.setProjectOwner(user);
        projectService.saveProject(project);
        return "redirect:/projects";

    }
    @GetMapping("/addproject")
    public String addProject(Model model){
        projectService.viewInit(model,null);
        return "addproject";

    }



    @GetMapping("/projects")
    public String fetchDepartments(Model model){
        List<Project> projectList = projectService.fetchProjects();
        model.addAttribute("projectList",projectList);
        return "AllProjects";
    }


    @GetMapping("/projects/{id}")
    public String fetchProjectById( @PathVariable("id") Long projectId , Model model) throws ProjectNotFoundException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        Project proDB =  projectService.fetchProjectById(projectId);
        if(proDB.getProjectOwner().getEmail().equals(loggedInUser.getName())){
            model.addAttribute("project",proDB);
             List<User> list    =  userService.fetchUsers();
             list.remove(proDB.getProjectOwner());
            model.addAttribute("alluser",list);
            return "editProject";
        }
        else{

            return "redirect:/projects";
        }



    }

    @GetMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId){
         projectService.deleteProject(projectId);
         return "redirect:/projects";
    }

    @PostMapping  ("/project/update")
    public String updateProject(@ModelAttribute("project") @Valid Project project){

            //initialize the set name dummy
//            Set<User> dummy = new HashSet<>();
//
//            for( User user : alluser){
//                    dummy.add(user);
//            }
//            project.setUsers(dummy);

            projectService.updateProject(project);
//            System.out.println("aaaaaaaaaaaaaa"+alluser);

            return "redirect:/projects";

    }
}
