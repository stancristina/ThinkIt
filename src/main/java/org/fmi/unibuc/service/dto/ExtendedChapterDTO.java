package org.fmi.unibuc.service.dto;

import java.util.List;

public class ExtendedChapterDTO extends ChapterDTO {

    private List<LessonDTO> lessons;

    public void setLessons(List<LessonDTO> lessons) {
        this.lessons = lessons;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }

}
