package com.mfajruramadhan.portfolio.service;

import com.mfajruramadhan.portfolio.exceptions.NotFoundException;
import com.mfajruramadhan.portfolio.interfaces.IProjectService;
import com.mfajruramadhan.portfolio.model.Project;
import com.mfajruramadhan.portfolio.model.ProjectDetails;
import com.mfajruramadhan.portfolio.repository.ProjectDetailsRepository;
import com.mfajruramadhan.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectDetailsRepository projectDetailsRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectDetailsRepository projectDetailsRepository) {
        this.projectRepository = projectRepository;
        this.projectDetailsRepository = projectDetailsRepository;
    }

    @Override
    public Project getProjectDetail(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new NotFoundException(String.format("Data with id %d is not found", id));
        }
        return project.get();
    }

    @Override
    public List<Project> getProjects() {
        List<Project> project = projectRepository.findAll();
        if (project.isEmpty()) {
            throw new NotFoundException("Data is empty");
        }
        return project;
    }

    @Override
    public void createProject(Project projectReq, ProjectDetails projectDetailsReq) {
        projectRepository.save(projectReq);
        projectDetailsRepository.save(projectDetailsReq);
    }

    @Override
    public boolean updateProject(Long id, Project projectReq, ProjectDetails projectDetailsReq) {
        Optional<Project> currData = projectRepository.findById(id);
        if (currData.isPresent()) {
            Project currProject = currData.get();
            ProjectDetails currProjectDetails = currData.get().getDetails();
            if (projectReq.getCategory() != null) currProject.setCategory(projectReq.getCategory());
            if (projectReq.getThumbnail() != null) currProject.setThumbnail(projectReq.getThumbnail());
            if (projectReq.getTitle() != null) currProject.setTitle(projectReq.getTitle());

            if (projectDetailsReq.getAttachment() != null) currProjectDetails.setAttachment(projectDetailsReq.getAttachment());
            if (projectDetailsReq.getDescription() != null) currProjectDetails.setDescription(projectDetailsReq.getDescription());
            if (projectDetailsReq.getDuration() != null) currProjectDetails.setDuration(projectDetailsReq.getDuration());
            if (projectDetailsReq.getRole() != null) currProjectDetails.setRole(projectDetailsReq.getRole());
            if (projectDetailsReq.getResponsibilities() != null) currProjectDetails.setResponsibilities(projectDetailsReq.getResponsibilities());
            if (projectDetailsReq.getStartDate() != null) currProjectDetails.setStartDate(projectDetailsReq.getStartDate());
            if (projectDetailsReq.getEndDate() != null) currProjectDetails.setEndDate(projectDetailsReq.getEndDate());

            projectRepository.save(currProject);
            projectDetailsRepository.save(currProjectDetails);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project proj = project.get();
            ProjectDetails projDetails = proj.getDetails();
            projectDetailsRepository.delete(projDetails);
            projectRepository.delete(proj);
            return true;
        }
        return false;
    }

}
