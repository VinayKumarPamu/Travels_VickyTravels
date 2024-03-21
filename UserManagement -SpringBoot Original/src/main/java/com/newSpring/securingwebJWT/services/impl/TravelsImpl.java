package com.newSpring.securingwebJWT.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newSpring.securingwebJWT.builder.YourClass;
import com.newSpring.securingwebJWT.dto.serialisible.JourneyDto;
import com.newSpring.securingwebJWT.dto.serialisible.UserSer;
import com.newSpring.securingwebJWT.services.Travels;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TravelsImpl implements Travels {

    @Value("${base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    @Override
    public String sendMultipartFile(MultipartFile multipartFile, String apiUrl, String description, UserSer userSer) throws IOException {
        // Convert MultipartFile to Resource
        String url = baseUrl + apiUrl;
        Resource resource = new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set up request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);
        body.add("Description",description);
        body.add("userId",userSer.getUserId());

        // Create HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Make POST request with RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Handle response
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("File uploaded successfully!");
            return ("File uploaded successfully!");
        } else {
            System.err.println("Failed to upload file. Status code: " + response.getStatusCode());
            System.err.println("Response body: " + response.getBody());
            return ("Failed to upload file. Status code: " + response.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<List<byte[]>> displayMultipartFile(String apiUrl, UserSer userSer) {
        String url = baseUrl + apiUrl;

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set up request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("userId", userSer.getUserId());

        // Create HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Make POST request with RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, requestEntity, byte[].class);

        // Initialize list to store byte arrays
        List<byte[]> imagesData = new ArrayList<>();

        // If the response is successful (200 OK), add the image data to the list
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            imagesData.add(responseEntity.getBody());
        }

        // Return ResponseEntity with the list of image data
        return ResponseEntity.ok(imagesData);
    }


    @Override
    public String userJrnyTransmitter(UserSer user, JourneyDto journeyDto, String endpoint) {
        String url = baseUrl + endpoint;

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create payload object
        YourClass payload = new YourClass();
        payload.setUser(user);
        payload.setJourneyDto(journeyDto);

        // Convert payload to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
        System.out.print(jsonPayload);
        // Create HttpEntity with headers and request body
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
        System.out.print(requestEntity);
        // Send HTTP POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String response = responseEntity.getBody();

        // Process the response as needed
        System.out.println("Response from receiver project: " + response);

        return response;
    }

    @Override
    public String userJrnyDeleteTransmitter(UserSer user, JourneyDto journeyDto, String endpoint) {
        String url = baseUrl + endpoint;

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create payload object
        YourClass payload = new YourClass();
        payload.setUser(user);
        payload.setJourneyDto(journeyDto);

        // Convert payload to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
        System.out.print(jsonPayload);
        // Create HttpEntity with headers and request body
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
        System.out.print(requestEntity);
        // Send HTTP POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        String response = responseEntity.getBody();

        // Process the response as needed
        System.out.println("Response from receiver project: " + response);

        return response;
    }

    @Override
    public List<JourneyDto> userTransmitter(UserSer user, String endpoint) {
        String url = baseUrl + endpoint;

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
        System.out.print(jsonPayload);

        // Create HttpEntity with headers and request body
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
        System.out.print(requestEntity);
        // Send HTTP POST request
        ResponseEntity<JourneyDto []> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JourneyDto[].class);
        List<JourneyDto> response = Arrays.asList(responseEntity.getBody());

        // Process the response as needed
        System.out.println("Response from receiver project: " + response);

        return response;
    }
}
