package net.javaguides.springboot.controller;



import net.javaguides.springboot.exception.ProjectNotFoundException;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProjectController {
    @Autowired
    private ProjectService projectService;


    @PostMapping("/saveproject")
    public String saveProject(@ModelAttribute("project") @Valid Project project , Model model){
        System.out.println("ggs");

        projectService.saveProject(project);
        System.out.println(project);

//        ModelAndView page = new ModelAndView("ALlProjects");
//        List<Project> projectList = projectService.fetchProjects();
//        model.addAttribute("projectList",projectList);
        return "redirect:/projects";

    }
    @GetMapping("/addproject")
    public String addProject(Model model){
//        ModelAndView page = new ModelAndView("AddProject");
//        model.addAttribute("project",new Project());
//        return page;
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
//        return projectService.fetchProjectById(departmentId);
        Project proDB =  projectService.fetchProjectById(projectId);
        model.addAttribute("project",proDB);
        return "editProject";

    }

    @GetMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId){
         projectService.deleteProject(projectId);
         return "redirect:/projects";
    }

    @PostMapping  ("/project/update")
    public String updateProject( @ModelAttribute("project") @Valid Project project){
         projectService.updateProject( project);
        System.out.println(project.getProjectName());

         return "redirect:/projects";
    }
}
