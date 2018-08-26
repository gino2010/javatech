package com.gino.moment.model;

import com.gino.moment.adapter.SectionAdapter;

import java.util.List;

public class GridData {
    private List<SectionAdapter.Section> sections;
    private List<Integer> imageIds;
    private String message;

    public GridData(List<SectionAdapter.Section> sections, List<Integer> imageIds, String message) {
        this.sections = sections;
        this.imageIds = imageIds;
        this.message = message;
    }

    public List<SectionAdapter.Section> getSections() {
        return sections;
    }

    public void setSections(List<SectionAdapter.Section> sections) {
        this.sections = sections;
    }

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
