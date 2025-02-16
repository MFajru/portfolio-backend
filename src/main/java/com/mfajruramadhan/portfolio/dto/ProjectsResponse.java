package com.mfajruramadhan.portfolio.dto;

import com.mfajruramadhan.portfolio.model.Project;

public class ProjectsResponse {
    private Long id;
    private String title;
    private String category;
    private String thumbnail;

    public ProjectsResponse() {
    }

    public ProjectsResponse(Long id, String title, String category, String thumbnail) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.thumbnail = thumbnail;
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


}
