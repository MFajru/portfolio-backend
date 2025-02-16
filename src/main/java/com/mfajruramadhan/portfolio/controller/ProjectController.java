package com.mfajruramadhan.portfolio.controller;

import com.mfajruramadhan.portfolio.dto.*;
import com.mfajruramadhan.portfolio.interfaces.IProjectService;
import com.mfajruramadhan.portfolio.model.Project;
import com.mfajruramadhan.portfolio.model.ProjectDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/detail/{id}", produces = "application/json")
    public ResponseEntity<Response<ProjectWithDetailsResponse>> getProjectDetail(@PathVariable Long id) {
        Project project = projectService.getProjectDetail(id);
        ProjectWithDetailsResponse pDetailsRes = new ProjectWithDetailsResponse(project.getId(), project.getTitle(), project.getCategory(), project.getThumbnail(), project.getDetails().getStartDate(), project.getDetails().getEndDate(), project.getDetails().getDuration(), project.getDetails().getRole(), project.getDetails().getResponsibilities(), project.getDetails().getDescription(), project.getDetails().getAttachment());
        Response<ProjectWithDetailsResponse> res = new Response<>();

        res.setMessage("Successfully getting data!");
        res.setData(pDetailsRes);
        return ResponseEntity.ok(res);
    }

    @GetMapping(value="", produces = "application/json")
    public ResponseEntity<Response<List<ProjectsResponse>>> getProjects() {
        List<Project> projects = projectService.getProjects();
        List<ProjectsResponse> response = new ArrayList<ProjectsResponse>();

        for (Project pro: projects) {
            response.add(new ProjectsResponse(pro.getId(), pro.getTitle(), pro.getCategory(), pro.getThumbnail()));
        }

        Response<List<ProjectsResponse>> res = new Response<>();
        res.setData(response);
        res.setMessage("Successfully getting data!");
        return ResponseEntity.ok(res);
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<PostResponse> addProject(@RequestBody AddProjectRequest request) {
        Project newProject = new Project();
        ProjectDetails newProjDetails = new ProjectDetails();

        request.mappingProjects(newProject, newProjDetails);
        projectService.createProject(newProject, newProjDetails);

        PostResponse res = new PostResponse();
        res.setMessage("Successfully created data!");
        return new ResponseEntity<PostResponse>(res, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/edit", produces = "application/json")
    public ResponseEntity<PostResponse> updateProject(@RequestBody AddProjectRequest request, @PathVariable Long id) {
        Project newProject = new Project();
        ProjectDetails newProjDetails = new ProjectDetails();

        request.mappingProjects(newProject, newProjDetails);
        projectService.updateProject(id, newProject, newProjDetails);

        PostResponse res = new PostResponse();
        res.setMessage("Successfully updated data!");

        return ResponseEntity.ok(res);
    }

    @DeleteMapping(value = "/{id}/delete", produces = "application/json")
    public ResponseEntity<PostResponse> deleteProject(@PathVariable Long id) {
        boolean isDeleted = projectService.deleteProject(id);
        PostResponse res = new PostResponse();

        if (isDeleted) {
            res.setMessage("Successfully deleted data!");
            return new ResponseEntity<PostResponse>(res, HttpStatus.OK);
        }
        String message = "Data with id " + id + " is not found. Failed to delete data";
        res.setMessage(message);
        return new ResponseEntity<PostResponse>(res, HttpStatus.NOT_FOUND);
    }
}
