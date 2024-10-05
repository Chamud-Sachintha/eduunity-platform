package com.eduunity.request;

import lombok.Data;

@Data
public class GenerateTopicContentRequest {
    private int moduleId;
    private String topicName;
}
