package com.eduunity;

import com.eduunity.request.GenerateModuleRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface GenerateModuleService {

    public ResponseEntity<Object> generateNewModule(GenerateModuleRequest generateModuleRequest);

    public ResponseEntity<Object> getAllGeneratedModulesByStudentId(String studentId);

    public ResponseEntity<Object> generateModuleContent(int moduleId, String moduleContentName);
}
