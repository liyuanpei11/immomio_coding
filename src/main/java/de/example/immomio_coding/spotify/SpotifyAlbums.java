package de.example.immomio_coding.spotify;

import de.example.immomio_coding.entities.Album;
import de.example.immomio_coding.repositories.AlbumRepository;
import de.example.immomio_coding.repositories.ArtistRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.Optional;

@Service
public class SpotifyAlbums extends SpotifyAPI {
    final AlbumRepository albumRepository;
    final ArtistRepository artistRepository;

    public SpotifyAlbums(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    public void fetchSpotifyArtistAlbums(String artistId) {
        String url = String.format("https://api.spotify.com/v1/artists/%s/albums", artistId);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("include_groups", "album")
                .encode()
                .toUriString();

        checkAccessToken();

        try {
            ResponseEntity<JsonNode> spotifyData = new RestTemplate().exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    this.httpEntity,
                    JsonNode.class
            );

            for (JsonNode spotifyAPIArtistAlbum :
                    Objects.requireNonNull(spotifyData.getBody()).get("items")) {
                Optional<Album> dbAlbum = albumRepository.findBySpotifyId(spotifyAPIArtistAlbum.get("id").textValue());
                if (dbAlbum.isEmpty()) {
                    albumRepository.save(updateAlbum(new Album(), spotifyAPIArtistAlbum, artistId));
                } else if (dbAlbum.get().isFetchFlag()) {
                    albumRepository.save(updateAlbum(dbAlbum.get(), spotifyAPIArtistAlbum, artistId));
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
        album.setArtist(artistRepository.findBySpotifyId(artistId).orElseThrow());
        album.setFetchFlag(true);
        return album;
    }
}
