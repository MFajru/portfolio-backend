package com.mfajruramadhan.portfolio.repository;

import com.mfajruramadhan.portfolio.dto.ProjectWithDetailsResponse;
import com.mfajruramadhan.portfolio.model.Project;
import com.mfajruramadhan.portfolio.model.ProjectDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, Long> {
//    @Query(value = "SELECT pd.* FROM projects p JOIN project_details pd ON p.id = pd.project_id", nativeQuery = true)
//    ProjectDetails findByIdWithDetails(Long id);
}
