package de.example.immomio_coding;


import de.example.immomio_coding.entities.Artist;
import de.example.immomio_coding.repositories.ArtistRepository;
import de.example.immomio_coding.spotify.SpotifyService;
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
public class ArtistRepositoryTests {

    @Autowired
    private ArtistRepository artistRepository;

    @MockBean
    private SpotifyService spotifyService;

    static UUID uuidTesting;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveArtistTest() {
        Artist artist = Artist.builder()
                .name("Test Artist")
                .spotifyId("123456asdasda789789")
                .popularity(69)
                .fetchFlag(false)
                .build();

        Artist savedArtist = artistRepository.save(artist);

        Assertions.assertNotNull(artist.getId());
        Assertions.assertEquals(artist, savedArtist);

        uuidTesting = artist.getId();
    }

    @Test
    @Order(2)
    public void getArtistTest() {
        Artist artist = artistRepository.findById(uuidTesting).orElseThrow();

        Assertions.assertEquals(artist.getId(), uuidTesting);
        Assertions.assertEquals(artist.getName(), "Test Artist");
        Assertions.assertEquals(artist.getSpotifyId(), "123456asdasda789789");
        Assertions.assertEquals(artist.getPopularity(), 69);
        Assertions.assertFalse(artist.isFetchFlag());
    }

    @Test
    @Order(3)
    public void ListOfArtistsTest() {
        List<Artist> artistList = artistRepository.findAll();

        Assertions.assertFalse(artistList.isEmpty());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateArtistTest() {
        Artist artist = artistRepository.findById(uuidTesting).orElseThrow();

        artist.setSpotifyId("15151515169696969");

        Artist updatedArtist = artistRepository.save(artist);

        Assertions.assertEquals(updatedArtist.getSpotifyId(), "15151515169696969");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteArtistTest() {
        Artist artist = artistRepository.findById(uuidTesting).orElseThrow();

        artistRepository.delete(artist);

        Artist deletedArtist = null;

        Optional<Artist> optionalArtist = artistRepository.findBySpotifyId("15151515169696969");

        if(optionalArtist.isPresent()) {
            deletedArtist = optionalArtist.get();
        }

        Assertions.assertNull(deletedArtist);
    }

}
