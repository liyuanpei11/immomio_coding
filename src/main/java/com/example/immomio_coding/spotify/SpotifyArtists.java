package com.example.immomio_coding.spotify;

import com.example.immomio_coding.dao.ArtistDAO;
import com.example.immomio_coding.entities.Artist;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class SpotifyArtists extends SpotifyAPI {
    ArtistDAO artistDAO;
    public SpotifyArtists(ArtistDAO artistDAO) {
        super();
        this.artistDAO = artistDAO;
    }

    @Scheduled(fixedRate = 10000)
    public void fetchSpotifyArtists() {
        List<String> artistsIdList = getArtistsIdList();

        String url = "https://api.spotify.com/v1/artists";

        //TODO: make params better
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("ids", String.join(",", artistsIdList))
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
            ResponseEntity<JsonNode> spotifyData = restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    this.httpEntity,
                    JsonNode.class
            );

            for (JsonNode spotifyAPIArtist :
                    Objects.requireNonNull(spotifyData.getBody()).get("artists")) {
                Artist dbArtist = artistDAO.findBySpotifyId(spotifyAPIArtist.get("id").toString());
                System.out.println("getting artist from db:");
                System.out.println(dbArtist);
                if (dbArtist == null) {
                    System.out.println("creating a new artist");
                    artistDAO.save(fetchSpotifyArtist(new Artist(), spotifyAPIArtist));
                } else if (dbArtist.isFetchFlag()) {
                    System.out.println("fetching existing artist");
                    artistDAO.save(fetchSpotifyArtist(dbArtist, spotifyAPIArtist));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Artist fetchSpotifyArtist(Artist dbArtist, JsonNode spotifyAPIArtist) {
        dbArtist.setName(spotifyAPIArtist.get("name").toString());
        dbArtist.setSpotifyId(spotifyAPIArtist.get("id").toString());
        dbArtist.setPopularity(spotifyAPIArtist.get("popularity").intValue());
        dbArtist.setFetchFlag(true);
        return dbArtist;
    }
}
