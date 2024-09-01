package com.eduunity.impl;

import com.eduunity.GenerateModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenerateModuleServiceImpl implements GenerateModuleService {

    private final RestTemplate restTemplate;

    public GenerateModuleServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Object> generateNewModule() {
        
        return null;
    }
}
