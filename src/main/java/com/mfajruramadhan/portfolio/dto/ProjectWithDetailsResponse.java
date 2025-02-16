package com.mfajruramadhan.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ProjectWithDetailsResponse {
    private Long id;
    private String title;
    private String category;
    private String thumbnail;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;
    private Integer duration;
    private String role;
    private String responsibilities;
    private String description;
    private String attachment;

    public ProjectWithDetailsResponse() {
    }

    public ProjectWithDetailsResponse(Long id, String title, String category, String thumbnail, Date startDate, Date endDate, Integer duration, String role, String responsibilities, String description, String attachment) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.thumbnail = thumbnail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.role = role;
        this.responsibilities = responsibilities;
        this.description = description;
        this.attachment = attachment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
