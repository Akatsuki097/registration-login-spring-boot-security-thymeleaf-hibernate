package net.javaguides.springboot.service;




import net.javaguides.springboot.exception.ProjectNotFoundException;
import net.javaguides.springboot.model.Project;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

public interface ProjectService {

    public void viewInit(Model model,  Project project) ;
    public  Project saveProject(Project project);

    public List<Project> fetchProjects();

    public Project fetchProjectById(Long projectId) throws ProjectNotFoundException;

    public String deleteProject(Long projectId);

    public Project updateProject( Project project);
}
