package com.mfajruramadhan.portfolio.repository;

import com.mfajruramadhan.portfolio.dto.ProjectWithDetailsResponse;
import com.mfajruramadhan.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
