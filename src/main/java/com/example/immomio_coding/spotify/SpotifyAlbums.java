package com.example.immomio_coding.spotify;

import com.example.immomio_coding.dao.AlbumDAO;
import com.example.immomio_coding.dao.ArtistDAO;
import com.example.immomio_coding.entities.Album;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
public class SpotifyAlbums extends SpotifyAPI {
    AlbumDAO albumDAO;
    ArtistDAO artistDAO;

    public SpotifyAlbums(AlbumDAO albumDAO, ArtistDAO artistDAO) {
        this.albumDAO = albumDAO;
        this.artistDAO = artistDAO;
    }

    public void fetchSpotifyArtistAlbums(String artistId) {
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
            ResponseEntity<JsonNode> spotifyData = restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    this.httpEntity,
                    JsonNode.class
            );
            System.out.println("GETTING ALBUM DATA RIGHT NOW YEAY");
            System.out.println(spotifyData);

            for (JsonNode spotifyAPIArtistAlbum :
                    Objects.requireNonNull(spotifyData.getBody()).get("items")) {
                System.out.println("getting album from db:");
                Album dbAlbum = albumDAO.findBySpotifyId(spotifyAPIArtistAlbum.get("id").textValue());
                System.out.println(dbAlbum);
                if (dbAlbum == null) {
                    System.out.println("creating new album");
                    albumDAO.save(updateAlbum(new Album(), spotifyAPIArtistAlbum, artistId));
                } else if (dbAlbum.isFetchFlag()) {
                    System.out.println("fetching existing album");
                    albumDAO.save(updateAlbum(dbAlbum, spotifyAPIArtistAlbum, artistId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Album updateAlbum(Album album, JsonNode spotifyAPIAlbum, String artistId) {
        album.setName(spotifyAPIAlbum.get("name").textValue());
        album.setSpotifyId(spotifyAPIAlbum.get("id").textValue());
        album.setTotalTracks(spotifyAPIAlbum.get("total_tracks").intValue());
        album.setArtist(artistDAO.findBySpotifyId(artistId));
        album.setFetchFlag(true);
        return album;
    }
}
