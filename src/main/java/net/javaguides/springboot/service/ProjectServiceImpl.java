package net.javaguides.springboot.service;


import net.javaguides.springboot.exception.ProjectNotFoundException;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void viewInit(Model model, Project project) {

            if (project == null) {
                project = new Project();
            }
            model.addAttribute(project);

    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> fetchProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project fetchProjectById(Long projectId) throws ProjectNotFoundException {

        Optional<Project> project = projectRepository.findById(projectId);
        if(!project.isPresent()){
            throw new ProjectNotFoundException("Project Not Found");
        }
        return project.get();
    }

    @Override
    public String deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
        return "Delete Successfulll";
    }

    @Override
    public Project updateProject( Project project) {
        Project proDB  = projectRepository.findById(project.getProjectId()).get();

        if(Objects.nonNull(project.getProjectName()) && !"".equalsIgnoreCase(project.getProjectName())){
            proDB.setProjectName(project.getProjectName());
        }
        if(Objects.nonNull(project.getIntro()) && !"".equalsIgnoreCase(project.getIntro())){
            proDB.setIntro(project.getIntro());
        }
        if(Objects.nonNull(project.getStartDateTime()) && !"".equalsIgnoreCase(project.getStartDateTime().toString())){
            proDB.setStartDateTime(project.getStartDateTime());
        }
        if(Objects.nonNull(project.getEndDateTime()) && !"".equalsIgnoreCase(project.getEndDateTime().toString())){
            proDB.setEndDateTime(project.getEndDateTime());
        }

        return projectRepository.save(proDB);

    }
}
