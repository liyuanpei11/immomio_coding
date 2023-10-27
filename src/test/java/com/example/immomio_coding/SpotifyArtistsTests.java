package com.example.immomio_coding;

import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.repositories.ArtistRepository;
import com.example.immomio_coding.spotify.SpotifyArtists;
import com.example.immomio_coding.spotify.SpotifyService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SpotifyArtistsTests {
    @Autowired
    private SpotifyArtists spotifyArtists;
    @MockBean
    private ArtistRepository artistRepository;
    @MockBean
    private SpotifyService spotifyService;
    static List<String> artistsIdList;

    @BeforeAll
    static void Setup() {
        artistsIdList = new ArrayList<>();
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
    }

    @Test
    public void fetchSpotifyArtistsTest() {
        spotifyArtists.fetchSpotifyArtists(artistsIdList);
        verify(artistRepository, atMost(10)).save(any(Artist.class));
    }

    @Test
    public void fetchSpotifyArtistsWithoutFetchFlagTest() {
        Artist demoArtist = Artist.builder()
                .id(UUID.randomUUID())
                .name("Demo Artist")
                .spotifyId("123456789abc")
                .popularity(1)
                .fetchFlag(false)
                .build();
        when(artistRepository.findBySpotifyId(anyString())).thenReturn(Optional.ofNullable(demoArtist));
        spotifyArtists.fetchSpotifyArtists(artistsIdList);
        verify(artistRepository, times(0)).save(any(Artist.class));
    }

    @Test
    public void fetchSpotifyArtistsWithFetchFlagTest() {
        Artist demoArtist = Artist.builder()
                .id(UUID.randomUUID())
                .name("Demo Artist")
                .spotifyId("123456789abc")
                .popularity(1)
                .fetchFlag(true)
                .build();
        when(artistRepository.findBySpotifyId(anyString())).thenReturn(Optional.ofNullable(demoArtist));
        spotifyArtists.fetchSpotifyArtists(artistsIdList);
        verify(artistRepository, times(10)).save(any(Artist.class));
    }

    @Test
    public void updateArtistTest() throws JsonProcessingException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();
        String newArtist = "{" +
                "\"name\": \"Test Name\"," +
                "\"id\": \"123456789abc\"," +
                "\"popularity\": 13" +
                "}";
        JsonNode artistNode = objectMapper.readTree(newArtist);
        Artist testArtist = new Artist();
        Artist updatedArtist = (Artist) getUpdateArtistMethod().invoke(spotifyArtists, testArtist, artistNode);
        Assertions.assertEquals(updatedArtist.getName(), "Test Name");
        Assertions.assertEquals(updatedArtist.getSpotifyId(), "123456789abc");
        Assertions.assertEquals(updatedArtist.getPopularity(), 13);
        Assertions.assertTrue(updatedArtist.isFetchFlag());
    }



    private Method getUpdateArtistMethod() throws NoSuchMethodException {
        Method method = SpotifyArtists.class.getDeclaredMethod("updateArtist", Artist.class, JsonNode.class);
        method.setAccessible(true);
        return method;
    }
}
