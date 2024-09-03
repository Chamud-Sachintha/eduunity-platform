package com.eduunity.request;

import lombok.Data;

@Data
public class GenerateModuleRequest {
    private String studentId;
    private String moduleName;
    private int experiancedLevel;
    private boolean isYoutubeVideosWanted;
}
