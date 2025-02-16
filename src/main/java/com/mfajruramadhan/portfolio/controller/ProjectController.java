package com.mfajruramadhan.portfolio.controller;

import com.mfajruramadhan.portfolio.dto.*;
import com.mfajruramadhan.portfolio.interfaces.IProjectService;
import com.mfajruramadhan.portfolio.model.Project;
import com.mfajruramadhan.portfolio.model.ProjectDetails;
import com.mfajruramadhan.portfolio.utils.ValidateDataTypes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        PostResponse res = new PostResponse();
        boolean isRequestValid = checkAddProjectRequest(request, res);

        if (!isRequestValid) {
            return new ResponseEntity<PostResponse>(res, HttpStatus.BAD_REQUEST);
        }

        mappingProjectAndDetails(newProject, newProjDetails, request);
        projectService.createProject(newProject, newProjDetails);
        res.setMessage("Successfully created data!");
        return new ResponseEntity<PostResponse>(res, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/edit", produces = "application/json")
    public ResponseEntity<PostResponse> updateProject(@RequestBody AddProjectRequest request, @PathVariable Long id) {
        Project newProject = new Project();
        ProjectDetails newProjDetails = new ProjectDetails();
        mappingProjectAndDetails(newProject, newProjDetails, request);
        boolean isExistAndDataUpdated =  projectService.updateProject(id, newProject, newProjDetails);
        PostResponse res = new PostResponse();

        if (isExistAndDataUpdated) {
            res.setMessage("Successfully updated data!");
            return ResponseEntity.ok(res);
        }
        String message = "Data with id " + id + " is not found. Failed to update data";
        res.setMessage(message);
        return new ResponseEntity<PostResponse>(res, HttpStatus.NOT_FOUND);

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


   private static void mappingProject(Project project, ProjectsResponse projectsResponse) {
       projectsResponse.setId(project.getId());
       projectsResponse.setCategory(project.getCategory());
       projectsResponse.setThumbnail(project.getThumbnail());
       projectsResponse.setTitle(project.getTitle());
   }

   private static void mappingProjectAndDetails(Project newProject, ProjectDetails newProjDetails, AddProjectRequest request) {
       newProject.setTitle(request.getTitle());
       newProject.setThumbnail(request.getThumbnail());
       newProject.setCategory(request.getCategory());

       newProjDetails.setAttachment(request.getAttachment());
       newProjDetails.setDescription(request.getDescription());
       newProjDetails.setDuration(request.getDuration());
       newProjDetails.setEndDate(request.getEndDate());
       newProjDetails.setStartDate(request.getStartDate());
       newProjDetails.setResponsibilities(request.getResponsibilities());
       newProjDetails.setRole(request.getRole());
       newProjDetails.setProject(newProject);
   }

    private static boolean checkAddProjectRequest(AddProjectRequest request, PostResponse response) {
        List<String> listMess = new ArrayList<>();

        if (request.getAttachment() == null) listMess.add("attachment");
        if (request.getThumbnail() == null) listMess.add("thumbnail");
        if (request.getResponsibilities() == null) listMess.add("responsibilities");
        if (request.getRole() == null) listMess.add("role");
        if (request.getDuration() == null) listMess.add("duration");
        if (request.getDescription() == null) listMess.add("description");
        if (request.getCategory() == null) listMess.add("category");
        if (request.getStartDate() == null) listMess.add("start_date");
        if (request.getEndDate() == null) listMess.add("end_date");
        if (request.getTitle() == null) listMess.add("title");

        String joinedMess = String.join(", ", listMess);
        response.setMessage(joinedMess + " must be filled!");

        return listMess.isEmpty();
    }
}
