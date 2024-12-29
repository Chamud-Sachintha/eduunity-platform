package com.eduunity.impl;

import com.eduunity.GenerateModuleService;
import com.eduunity.dto.NewModule;
import com.eduunity.dto.TopicContent;
import com.eduunity.repo.GenerateTopicContentRepository;
import com.eduunity.repo.GeneratedModuleRepository;
import com.eduunity.repo.StudentRepository;
import com.eduunity.request.GenerateModuleRequest;
import com.eduunity.request.GenerateTopicContentRequest;
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

import java.util.*;

@Service
public class GenerateModuleServiceImpl implements GenerateModuleService {

    @Autowired
    private GeneratedModuleRepository generatedModuleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GenerateTopicContentRepository generateTopicContentRepository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GenerateModuleServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<Object> generateModuleContent(int moduleId, String moduleContentName) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();
        NewModule newModule = new NewModule();

        Optional<NewModule> generatedModuleInfo = this.generatedModuleRepository.findById(moduleId);

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyB5eaSHI8uHBLzYzWncuHwsB9hCbPooL6Y"; // Replace YOUR_API_KEY with your actual API key

        Optional<TopicContent> getGeneratedTopicContent = this.generateTopicContentRepository.findByModuleIdAndTopicName(moduleId, moduleContentName);

        try {
            if (getGeneratedTopicContent.isPresent()) {
                finalRespObj.put("code", 1);
                finalRespObj.put("message", "Success");
                finalRespObj.put("data", objectMapper.readTree(getGeneratedTopicContent.get().getContent()));
                finalRespObj.put("links", objectMapper.readTree(getGeneratedTopicContent.get().getThirdPartyLinks()));

                return ResponseEntity.ok(finalRespObj);
            }
        } catch (Exception e) {
            finalRespObj.put("status", 0);
            finalRespObj.put("message", "Error Occure" + e.getMessage());

            return ResponseEntity.ok(finalRespObj);
        }

        if (generatedModuleInfo.isPresent()) {
            // Create payload
            String formatedPayloadTextContent = generateModuleContentSentence(moduleContentName, generatedModuleInfo.get().getExperiancedLevel());
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
            String moduleContent = null;
            Object thirdPartyLinks = null;
            try {
                // Parse the response body
                RootResponse rootResponse = objectMapper.readValue(response.getBody(), RootResponse.class);

                if (rootResponse.getCandidates() != null && !rootResponse.getCandidates().isEmpty()) {
                    moduleContent = rootResponse.getCandidates().get(0).getContent().getParts().get(0).getText();

                    thirdPartyLinks = this.generateRefernceLinksForContent(moduleContentName);
                    String jsonStringConverted = objectMapper.writeValueAsString(moduleContent);
                    String jsonStringConverted2 = objectMapper.writeValueAsString(thirdPartyLinks);

                    TopicContent generateTopicContentRequest = new TopicContent();

                    generateTopicContentRequest.setModuleId(moduleId);
                    generateTopicContentRequest.setTopicName(moduleContentName);
                    generateTopicContentRequest.setContent(jsonStringConverted);
                    generateTopicContentRequest.setThirdPartyLinks(jsonStringConverted2);

                    this.generateTopicContentRepository.save(generateTopicContentRequest);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            finalRespObj.put("code", 1);
            finalRespObj.put("message", "Success");
            finalRespObj.put("data", moduleContent);
            finalRespObj.put("links", thirdPartyLinks);
        } else {
            finalRespObj.put("status", 0);
            finalRespObj.put("message", "Module not found");
        }

        return ResponseEntity.ok(finalRespObj);
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

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyB5eaSHI8uHBLzYzWncuHwsB9hCbPooL6Y"; // Replace YOUR_API_KEY with your actual API key

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

    @Override
    public ResponseEntity<Object> getAllTopicsByModuleId(String moduleId) {

        HashMap<String, Object> finalRespObj = new LinkedHashMap<>();
        HashMap<String, Object> formatedTopicList = new HashMap<>();

        int moduleIdInt;
        try {
            moduleIdInt = Integer.parseInt(moduleId);
        } catch (NumberFormatException e) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "Invalid module ID");
            return ResponseEntity.badRequest().body(finalRespObj);
        }

        Optional<NewModule> getModuleInfo = this.generatedModuleRepository.findById(moduleIdInt);

        if (getModuleInfo.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode getModuleContent = null;

            try {
                getModuleContent = objectMapper.readTree(getModuleInfo.get().getModuleContent());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String experiancedLevelText = null;

            if (getModuleInfo.get().getExperiancedLevel() == 1) {
                experiancedLevelText = "Beginner Level";
            } else if (getModuleInfo.get().getExperiancedLevel() == 2) {
                experiancedLevelText = "Intermidiate Level";
            } else if (getModuleInfo.get().getExperiancedLevel() == 3) {
                experiancedLevelText = "End Level";
            }

            formatedTopicList.put("moduleContent", getModuleContent);
            formatedTopicList.put("experiancedLevel", experiancedLevelText);

            finalRespObj.put("code", 1);
            finalRespObj.put("message", "sucess");
            finalRespObj.put("content", formatedTopicList);
        } else {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "Module not found");
        }

        return ResponseEntity.ok(finalRespObj);
    }

    public Optional<NewModule> getModuleInfoByModuleId(int moduleId) {

        Optional<NewModule> moduleInfo = this.generatedModuleRepository.findById(moduleId);

        if (moduleInfo.isPresent()) {
            return moduleInfo;
        } else {
            return null;
        }
    }

    private Object generateRefernceLinksForContent(String topic) {
        String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCmKsHUxa1cP2pi3mZPfa5ox1uK5Z3Qw0c&cx=f2945890e1d9e4d71&q=" + topic;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        return response.getBody().get("items");
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

        return text + " " + experiancedLevelText + " give me 10 modules and like json format rename module name as title ino need any other content i need all the titles are inside modules remember this i want each module inside title block";
    }

    private String generateModuleContentSentence(String moduleName, int expLevel) {

        String finalSentence = null;
        String experiancedLevelText = null;

        if (expLevel == 1) {
            experiancedLevelText = "Basic";
        } else if (expLevel == 2) {
            experiancedLevelText = "Intermediate";
        } else {
            experiancedLevelText = "Advanced";
        }

        finalSentence = "Explain full tutorial for " + moduleName + " " + experiancedLevelText;
        return finalSentence;
    }
}
