package com.example.immomio_coding.spotify;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SpotifyAPI {

    String accessToken;
    LocalDateTime acquireDateTime;
    int expireTime;
    HttpEntity httpEntity;

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

            System.out.println("Token is: " + this.accessToken);
            System.out.println("expires in: " + this.expireTime);
            headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + this.accessToken);
            this.httpEntity = new HttpEntity<>(headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchSpotifyAlbums(String artistId) {
        String url = String.format("https://api.spotify.com/v1/artists/%s/albums", artistId);

        System.out.println(url);

        //TODO: make params better
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("include_groups", "album")
                .encode()
                .toUriString();

        //TODO: maybe SpringBoot has a function for this
        if (this.accessToken == null ||
                ChronoUnit.SECONDS.between(Objects.requireNonNull(this.acquireDateTime), LocalDateTime.now()) >= this.expireTime) {
            System.out.println("getting a new token");
            this.generateAccessToken();
        }

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> spotifyData = restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    this.httpEntity,
                    String.class
            );
            System.out.println(spotifyData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<String> getArtistsIdList() {
        List<String> artistsIdList = new ArrayList<>();
        artistsIdList.add("4UXqAaa6dQYAk18Lv7PEgX"); // Fall Out Boy
        artistsIdList.add("378dH6EszOLFShpRzAQkVM"); // Lindsey Stirling
        artistsIdList.add("1yxSLGMDHlW21z4YXirZDS"); // Black Eyed Pease
        artistsIdList.add("3qm84nBOXUEQ2vnTfUTTFC"); // Guns n Roses
        artistsIdList.add("3TOqt5oJwL9BE2NG9MEwDa"); // Disturbed
        artistsIdList.add("6XyY86QOPPrYVGvF9ch6wz"); // Linkin Park
        artistsIdList.add("58lV9VcRSjABbAbfWS6skp"); // Bon Jovi
        artistsIdList.add("1dfeR4HaWDbWqFHLkxsg1d"); // Queen
        artistsIdList.add("53XhwfbYqKCa1cC15pYq2q"); // Imagine Dragons
        artistsIdList.add("711MCceyCBcFnzjGY4Q7Un"); // ACDC
        return artistsIdList;
    }
}
