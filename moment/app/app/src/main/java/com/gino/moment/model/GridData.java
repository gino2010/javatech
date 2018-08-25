package com.gino.moment.model;

import com.gino.moment.adapter.SectionAdapter;

import java.util.List;

public class GridData {
    List<SectionAdapter.Section> sections;
    List<Integer> imageIds;

    public GridData(List<SectionAdapter.Section> sections, List<Integer> imageIds) {
        this.sections = sections;
        this.imageIds = imageIds;
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
}
