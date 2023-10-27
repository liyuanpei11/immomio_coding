package de.example.immomio_coding;

import de.example.immomio_coding.entities.Album;
import de.example.immomio_coding.entities.Artist;
import de.example.immomio_coding.repositories.AlbumRepository;
import de.example.immomio_coding.repositories.ArtistRepository;
import de.example.immomio_coding.spotify.SpotifyAlbums;
import de.example.immomio_coding.spotify.SpotifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SpotifyAlbumTests {
    @Autowired
    private SpotifyAlbums spotifyAlbums;
    @MockBean
    private AlbumRepository albumRepository;
    @MockBean
    private ArtistRepository artistRepository;
    @MockBean
    private SpotifyService spotifyService;
    static Artist dbArtist;

    @BeforeAll
    static void Setup() {
        dbArtist = Artist.builder()
                .id(UUID.randomUUID())
                .name("Fall Out Boy")
                .spotifyId("4UXqAaa6dQYAk18Lv7PEgX")
                .popularity(22)
                .fetchFlag(true)
                .build();
    }

    @Test
    public void fetchSpotifyArtistAlbumsTest() {
        when(artistRepository.findBySpotifyId("4UXqAaa6dQYAk18Lv7PEgX")).thenReturn(Optional.ofNullable(dbArtist));
        spotifyAlbums.fetchSpotifyArtistAlbums("4UXqAaa6dQYAk18Lv7PEgX");
        verify(albumRepository, atMost(14)).save(any(Album.class));
    }

    @Test
    public void fetchSpotifyArtistAlbumsWithoutFetchFlagTest() {
        Album dbAlbum = Album.builder()
                .id(UUID.randomUUID())
                .name("Save Rock And Roll")
                .spotifyId("5jKMfS57mHTHzlSFGfPFxU")
                .totalTracks(11)
                .artist(Artist.builder()
                        .id(UUID.randomUUID())
                        .name("Fall Out Boy")
                        .spotifyId("4UXqAaa6dQYAk18Lv7PEgX")
                        .popularity(1)
                        .fetchFlag(true)
                        .build()
                )
                .fetchFlag(false)
                .build();
        when(albumRepository.findBySpotifyId(anyString())).thenReturn(Optional.ofNullable(dbAlbum));
        spotifyAlbums.fetchSpotifyArtistAlbums("4UXqAaa6dQYAk18Lv7PEgX");
        verify(albumRepository, times(0)).save(any(Album.class));
    }

    @Test
    public void fetchSpotifyArtistAlbumsWithFetchFlagTest() {
        when(artistRepository.findBySpotifyId("4UXqAaa6dQYAk18Lv7PEgX")).thenReturn(Optional.ofNullable(dbArtist));
        Album dbAlbum = Album.builder()
                .id(UUID.randomUUID())
                .name("Save Rock And Roll")
                .spotifyId("5jKMfS57mHTHzlSFGfPFxU")
                .totalTracks(11)
                .artist(dbArtist)
                .fetchFlag(true)
                .build();
        when(albumRepository.findBySpotifyId(anyString())).thenReturn(Optional.ofNullable(dbAlbum));
        spotifyAlbums.fetchSpotifyArtistAlbums("4UXqAaa6dQYAk18Lv7PEgX");
        verify(albumRepository, times(14)).save(any(Album.class));
    }

    @Test
    public void updateAlbumTest() throws JsonProcessingException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();
        String newAlbum = "{" +
                "\"name\": \"Test Album\"," +
                "\"id\": \"123456789abc\"," +
                "\"total_tracks\": 12" +
                "}";
        JsonNode albumNode = objectMapper.readTree(newAlbum);
        Album testAlbum = new Album();
        when(artistRepository.findBySpotifyId("4UXqAaa6dQYAk18Lv7PEgX")).thenReturn(Optional.ofNullable(dbArtist));
        Album updatedAlbum = (Album) getUpdateAlbumMethod().invoke(spotifyAlbums, testAlbum, albumNode, "4UXqAaa6dQYAk18Lv7PEgX");
        Assertions.assertEquals(updatedAlbum.getName(), "Test Album");
        Assertions.assertEquals(updatedAlbum.getSpotifyId(), "123456789abc");
        Assertions.assertEquals(updatedAlbum.getTotalTracks(), 12);
        Assertions.assertEquals(updatedAlbum.getArtist().getName(), "Fall Out Boy");
        Assertions.assertEquals(updatedAlbum.getArtist().getSpotifyId(), "4UXqAaa6dQYAk18Lv7PEgX");
        Assertions.assertEquals(updatedAlbum.getArtist().getPopularity(), 22);
        Assertions.assertTrue(updatedAlbum.getArtist().isFetchFlag());
        Assertions.assertTrue(updatedAlbum.isFetchFlag());
    }

    private Method getUpdateAlbumMethod() throws NoSuchMethodException {
        Method method = SpotifyAlbums.class.getDeclaredMethod("updateAlbum", Album.class, JsonNode.class, String.class);
        method.setAccessible(true);
        return method;
    }
}
