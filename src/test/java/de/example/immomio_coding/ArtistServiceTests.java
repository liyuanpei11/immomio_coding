package de.example.immomio_coding;

import de.example.immomio_coding.entities.Artist;
import de.example.immomio_coding.repositories.ArtistRepository;
import de.example.immomio_coding.services.ArtistService;
import de.example.immomio_coding.spotify.SpotifyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ArtistServiceTests {
    @Autowired
    private ArtistService artistService;
    @MockBean
    private ArtistRepository artistRepository;
    @MockBean
    private SpotifyService spotifyService;

    static Artist artist;
    static UUID uuid;

    @BeforeAll
    static void Setup() {
        uuid = UUID.randomUUID();
        artist = Artist.builder()
                .id(uuid)
                .name("Test Artist")
                .spotifyId("123456asdasda789789")
                .popularity(69)
                .fetchFlag(false)
                .build();
    }

    @Test
    public void getArtistsTest() {
        List<Artist> artistList = new ArrayList<>();
        artistList.add(artist);
        artistList.add(Artist.builder()
                .id(UUID.randomUUID())
                .name("Test Artist 2")
                .spotifyId("8887777AAABBBB")
                .popularity(1)
                .fetchFlag(false)
                .build()
        );
        when(artistRepository.findAll()).thenReturn(artistList);
        Assertions.assertFalse(artistService.getArtists().isEmpty());
        Assertions.assertEquals(artistService.getArtists().get(0), artist);
        Assertions.assertEquals(artistService.getArtists().get(1).getName(), "Test Artist 2");
        Assertions.assertEquals(artistService.getArtists().get(1).getSpotifyId(), "8887777AAABBBB");
        Assertions.assertEquals(artistService.getArtists().get(1).getPopularity(), 1);
        Assertions.assertFalse(artistService.getArtists().get(1).isFetchFlag());
    }

    @Test
    public void getArtistTest() {
        when(artistRepository.findById(uuid)).thenReturn(Optional.ofNullable(artist));
        Assertions.assertEquals(artistService.getArtistById(uuid), artist);
    }

    @Test
    public void createArtistTest() {
        Artist artistWithoutUUID = Artist.builder()
                .name("Test New Artist")
                .spotifyId("123456asdasda789789")
                .popularity(69)
                .fetchFlag(false)
                .build();
        when(artistRepository.save(artistWithoutUUID)).thenReturn(artist);
        Assertions.assertEquals(artistService.createArtist(artistWithoutUUID), artist);
    }

    @Test
    public void updateArtistTest() {
        Artist updatedArtist = Artist.builder()
                .id(uuid)
                .name("Test updated Artist")
                .spotifyId("778984254546YY")
                .popularity(12)
                .fetchFlag(true)
                .build();
        Artist finalUpdatedArtist = Artist.builder()
                .id(updatedArtist.getId())
                .name(updatedArtist.getName())
                .spotifyId(updatedArtist.getSpotifyId())
                .popularity(updatedArtist.getPopularity())
                .fetchFlag(false)
                .build();
        when(artistRepository.findById(uuid)).thenReturn(Optional.ofNullable(artist));
        Assertions.assertEquals(artistService.updateArtist(uuid, updatedArtist), finalUpdatedArtist);
    }

    @Test
    public void deleteArtistTest() {
        when(artistRepository.findById(uuid)).thenReturn(Optional.ofNullable(artist));
        Assertions.assertEquals(artistService.deleteArtist(uuid), artist);
    }

    @Test
    public void searchArtistTest() {
        List<Artist> artistList = new ArrayList<>();
        artistList.add(artist);
        when(artistRepository.searchByContent("Test")).thenReturn(artistList);
        Assertions.assertEquals(1, artistService.searchArtists("Test").size());
        Assertions.assertEquals(artistService.searchArtists("Test").get(0), artist);
    }
}
