package com.mfajruramadhan.portfolio.interfaces;

import com.mfajruramadhan.portfolio.model.Project;
import com.mfajruramadhan.portfolio.model.ProjectDetails;

import java.util.List;

public interface IProjectService {
    Project getProjectDetail(Long id);
    List<Project> getProjects();
    void createProject(Project projectReq, ProjectDetails projectDetailsReq);
    void updateProject(Long id, Project projectReq, ProjectDetails projectDetailsReq);
    boolean deleteProject(Long id);

}
