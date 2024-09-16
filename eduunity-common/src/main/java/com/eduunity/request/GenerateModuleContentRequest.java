package com.eduunity.request;

import lombok.Data;

@Data
public class GenerateModuleContentRequest {
    private String moduleId;
    private String moduleContentName;
}
