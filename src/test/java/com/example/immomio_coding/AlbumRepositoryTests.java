package com.example.immomio_coding;

import com.example.immomio_coding.entities.Album;
import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.repositories.AlbumRepository;
import com.example.immomio_coding.repositories.ArtistRepository;
import com.example.immomio_coding.spotify.SpotifyService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@ActiveProfiles("test-containers-flyway")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlbumRepositoryTests {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @MockBean
    private SpotifyService spotifyService;

    static UUID uuidTesting;

    static Artist artistTesting;

    @BeforeAll
    public static void beforeTesting() {
        artistTesting = Artist.builder()
                .name("Test Artist")
                .spotifyId("123456asdasda789789")
                .popularity(69)
                .fetchFlag(false)
                .build();
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveAlbumTest() {
        artistTesting = artistRepository.save(artistTesting);
        Album album = Album.builder()
                .name("Test Album")
                .spotifyId("987987987pppp7897789")
                .totalTracks(11)
                .artist(artistTesting)
                .fetchFlag(false)
                .build();

        Album savedAlbum = albumRepository.save(album);

        Assertions.assertNotNull(album.getId());
        Assertions.assertEquals(album, savedAlbum);

        uuidTesting = album.getId();
    }

    @Test
    @Order(2)
    public void getAlbumTest() {
        Album album = albumRepository.findById(uuidTesting).orElseThrow();

        Assertions.assertEquals(album.getId(), uuidTesting);
        Assertions.assertEquals(album.getName(), "Test Album");
        Assertions.assertEquals(album.getSpotifyId(), "987987987pppp7897789");
        Assertions.assertEquals(album.getTotalTracks(), 11);
        Assertions.assertEquals(album.getArtist(), artistTesting);
        Assertions.assertFalse(album.isFetchFlag());
    }

    @Test
    @Order(3)
    public void ListOfAlbumTest() {
        List<Album> albumList = albumRepository.findAll();

        Assertions.assertFalse(albumList.isEmpty());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateAlbumTest() {
        Album album = albumRepository.findById(uuidTesting).orElseThrow();

        album.setSpotifyId("15151515169696969");

        Album updatedAlbum = albumRepository.save(album);

        Assertions.assertEquals(updatedAlbum.getSpotifyId(), "15151515169696969");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteAlbumTest() {
        Album album = albumRepository.findById(uuidTesting).orElseThrow();

        albumRepository.delete(album);

        Album deletedAlbum = null;

        Optional<Album> optionalArtist = albumRepository.findBySpotifyId("15151515169696969");

        if(optionalArtist.isPresent()) {
            deletedAlbum = optionalArtist.get();
        }

        Assertions.assertNull(deletedAlbum);
    }
}
