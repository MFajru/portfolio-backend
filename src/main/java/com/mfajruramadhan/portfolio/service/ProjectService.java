package com.mfajruramadhan.portfolio.service;

import com.mfajruramadhan.portfolio.exceptions.BadRequestException;
import com.mfajruramadhan.portfolio.exceptions.NotFoundException;
import com.mfajruramadhan.portfolio.interfaces.IProjectService;
import com.mfajruramadhan.portfolio.model.Project;
import com.mfajruramadhan.portfolio.model.ProjectDetails;
import com.mfajruramadhan.portfolio.repository.ProjectDetailsRepository;
import com.mfajruramadhan.portfolio.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void createProject(Project project, ProjectDetails projectDetails) {
        List<String> listMess = new ArrayList<>();

        if (projectDetails.getAttachment() == null) listMess.add("attachment");
        if (project.getThumbnail() == null) listMess.add("thumbnail");
        if (projectDetails.getResponsibilities() == null) listMess.add("responsibilities");
        if (projectDetails.getRole() == null) listMess.add("role");
        if (projectDetails.getDuration() == null) listMess.add("duration");
        if (projectDetails.getDescription() == null) listMess.add("description");
        if (project.getCategory() == null) listMess.add("category");
        if (projectDetails.getStartDate() == null) listMess.add("start_date");
        if (projectDetails.getEndDate() == null) listMess.add("end_date");
        if (project.getTitle() == null) listMess.add("title");

        if (!listMess.isEmpty()) {
            String joinedMess = String.join(", ", listMess) + " must be filled!";
            throw new BadRequestException(joinedMess);
        }

        projectRepository.save(project);
        projectDetailsRepository.save(projectDetails);
    }

    @Override
    public void updateProject(Long id, Project projectReq, ProjectDetails projectDetailsReq) {
        Optional<Project> currData = projectRepository.findById(id);

        if (currData.isEmpty()) {
            throw new NotFoundException("Data with id " + id + " is not found. Failed to update data");
        }

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
