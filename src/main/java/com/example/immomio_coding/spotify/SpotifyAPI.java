package com.example.immomio_coding.spotify;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class SpotifyAPI {

    String accessToken;
    LocalDateTime acquireDateTime;
    int expireTime;
    HttpEntity<String> httpEntity;

    void generateAccessToken() {
        String url = "https://accounts.spotify.com/api/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> data= new LinkedMultiValueMap<>();
        data.add("grant_type", "client_credentials");
        data.add("client_id", "5ff661307fe842828acd26dc4c67295b");
        data.add("client_secret", "318cfd39a11446c8b4e23a2971618f6e");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);

        String response = restTemplate.postForObject(url, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(response);
            this.accessToken = root.path("access_token").asText();
            this.expireTime = root.path("expires_in").asInt();
            this.acquireDateTime = LocalDateTime.now();
            headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + this.accessToken);
            this.httpEntity = new HttpEntity<>(headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void checkAccessToken() {
        if (this.accessToken == null ||
                ChronoUnit.SECONDS.between(Objects.requireNonNull(this.acquireDateTime), LocalDateTime.now()) >= this.expireTime) {
            this.generateAccessToken();
        }
    }
}
