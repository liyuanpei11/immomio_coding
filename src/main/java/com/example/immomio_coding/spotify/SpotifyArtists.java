package com.example.immomio_coding.spotify;

import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.repositories.ArtistRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SpotifyArtists extends SpotifyAPI {
    ArtistRepository artistRepository;
    public SpotifyArtists(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public void fetchSpotifyArtists(List<String> artistsIdList) {
        String url = "https://api.spotify.com/v1/artists";

        //TODO: make params better
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("ids", String.join(",", artistsIdList))
                .encode()
                .toUriString();

        //TODO: maybe SpringBoot has a function for this
        if (this.accessToken == null ||
                ChronoUnit.SECONDS.between(Objects.requireNonNull(this.acquireDateTime), LocalDateTime.now()) >= this.expireTime) {
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
                Optional<Artist> dbArtist = artistRepository.findBySpotifyId(spotifyAPIArtist.get("id").textValue());
                if (dbArtist.isEmpty()) {
                    artistRepository.save(updateArtist(new Artist(), spotifyAPIArtist));
                } else if (dbArtist.get().isFetchFlag()) {
                    artistRepository.save(updateArtist(dbArtist.get(), spotifyAPIArtist));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Artist updateArtist(Artist dbArtist, JsonNode spotifyAPIArtist) {
        dbArtist.setName(spotifyAPIArtist.get("name").textValue());
        dbArtist.setSpotifyId(spotifyAPIArtist.get("id").textValue());
        dbArtist.setPopularity(spotifyAPIArtist.get("popularity").intValue());
        dbArtist.setFetchFlag(true);
        return dbArtist;
    }
}
