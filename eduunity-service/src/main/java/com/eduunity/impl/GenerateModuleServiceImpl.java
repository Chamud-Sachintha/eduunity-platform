package com.eduunity.impl;

import com.eduunity.GenerateModuleService;
import com.eduunity.dto.NewModule;
import com.eduunity.repo.GeneratedModuleRepository;
import com.eduunity.repo.StudentRepository;
import com.eduunity.request.GenerateModuleRequest;
import com.eduunity.response.Content;
import com.eduunity.response.ContentRequest;
import com.eduunity.response.Part;
import com.eduunity.response.RootResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenerateModuleServiceImpl implements GenerateModuleService {

    @Autowired
    private GeneratedModuleRepository generatedModuleRepository;

    @Autowired
    private StudentRepository studentRepository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GenerateModuleServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<Object> getAllGeneratedModulesByStudentId(String studentId) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();
        List<Object> formatedModuleList = new ArrayList<>();

        List<NewModule> generatedModuleList = this.generatedModuleRepository.findAllByStudentId(Integer.parseInt(studentId));
        for (NewModule newModule : generatedModuleList) {
            Map<String, Object> outputObj = new LinkedHashMap<String, Object>();

            outputObj.put("id", newModule.getId());
            outputObj.put("moduleName", newModule.getModuleName());

            JsonNode formatedContent = null;

            try {
               formatedContent  = convertStringToJson(newModule.getModuleContent());
            } catch (Exception e) {
                e.printStackTrace();
            }

            outputObj.put("moduleContent", formatedContent);
            outputObj.put("experiancedLevel", newModule.getExperiancedLevel());
            outputObj.put("isWantYouTubeLinks", newModule.isYoutubeVideosWanted());

            formatedModuleList.add(outputObj);
        }

        finalRespObj.put("code", 1);
        finalRespObj.put("message", "sucess");
        finalRespObj.put("content", formatedModuleList);

        return ResponseEntity.ok(finalRespObj);
    }

    @Override
    public ResponseEntity<Object> generateNewModule(GenerateModuleRequest generateModuleRequest) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();
        NewModule newModule = new NewModule();

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyBIiqGItoZ3QDlcr0kDoXAGdSneZwGmRQk"; // Replace YOUR_API_KEY with your actual API key

        // Create payload
        String formatedPayloadTextContent = configPartName(generateModuleRequest.getExperiancedLevel(), generateModuleRequest.getModuleName());
        Part part = new Part(formatedPayloadTextContent);
        Content content = new Content();
        content.setParts(List.of(part));
        ContentRequest contentRequest = new ContentRequest();
        contentRequest.setContents(List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the request entity
        HttpEntity<ContentRequest> request = new HttpEntity<>(contentRequest, headers);

        // Send the request and get the response
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        try {
            // Parse the response body
            RootResponse rootResponse = objectMapper.readValue(response.getBody(), RootResponse.class);
            if (rootResponse.getCandidates() != null && !rootResponse.getCandidates().isEmpty()) {
                JsonNode jsonNode = getJsonNode(rootResponse);

                newModule.setModuleName(generateModuleRequest.getModuleName());
                newModule.setExperiancedLevel(generateModuleRequest.getExperiancedLevel());
                newModule.setYoutubeVideosWanted(generateModuleRequest.isYoutubeVideosWanted());
                newModule.setStudent(this.studentRepository.findById(Integer.parseInt(generateModuleRequest.getStudentId())).orElse(null));

                String jsonStringConverted = objectMapper.writeValueAsString(jsonNode);

                newModule.setModuleContent(jsonStringConverted);

                NewModule generatedModule = this.generatedModuleRepository.save(newModule);

                if (generatedModule.getModuleName() != null) {
                    finalRespObj.put("code", 1);
                    finalRespObj.put("message", "Success");
                    finalRespObj.put("data", jsonNode);
                } else {
                    finalRespObj.put("code", 0);
                    finalRespObj.put("message", "Error");
                    finalRespObj.put("data", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(finalRespObj);
    }

    private static JsonNode getJsonNode(RootResponse rootResponse) throws JsonProcessingException {
        String jsonResponse = rootResponse.getCandidates().get(0).getContent().getParts().get(0).getText();

        // Clean up the response to extract the JSON
        if (jsonResponse.startsWith("```json")) {
            jsonResponse = jsonResponse.substring(7, jsonResponse.length() - 3).trim();  // Remove the ```JSON and ``` markers
        }

        // Step 2: Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Step 3: Parse the JSON response
        return objectMapper.readTree(jsonResponse);
    }

    private static JsonNode convertStringToJson(String moduleContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Step 3: Parse the JSON response
        return objectMapper.readTree(moduleContent);
    }

    private String configPartName(int expLevel, String text) {
        String experiancedLevelText = null;

        if (expLevel == 1) {
            experiancedLevelText = "basic";
        } else if (expLevel == 2) {
            experiancedLevelText = "intermediate";
        } else {
            experiancedLevelText = "advanced";
        }

        return text + " " + experiancedLevelText + " give me modules and like json format rename module name as title";
    }
}
