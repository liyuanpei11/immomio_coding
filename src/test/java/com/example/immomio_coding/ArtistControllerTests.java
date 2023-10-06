package com.example.immomio_coding;


import com.example.immomio_coding.controller.ArtistController;
import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.services.ArtistService;
import com.example.immomio_coding.spotify.SpotifyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtistController.class)
public class ArtistControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArtistService artistService;
    @MockBean
    private SpotifyService spotifyService;

    static Artist artist;
    static UUID uuidTesting;
    static ObjectMapper objectMapper;
    static String urlTemplate = "/artists";

    @BeforeAll
    public static void beforeTesting() {
        uuidTesting = UUID.randomUUID();
        artist = Artist.builder()
                .id(uuidTesting)
                .name("Test Artist")
                .spotifyId("123456asdasda789789")
                .popularity(69)
                .fetchFlag(false)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getArtists() throws Exception {
        List<Artist> artistList = new ArrayList<>();
        artistList.add(artist);
        artistList.add(Artist.builder()
                .id(UUID.randomUUID())
                .name("Test Artist 2")
                .spotifyId("99988777DDD555444")
                .popularity(1)
                .fetchFlag(true)
                .build());
        when(artistService.getArtists()).thenReturn(artistList);
        this.mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(artistList)));
    }

    @Test
    public void getArtist() throws Exception {
        when(artistService.getArtistById(uuidTesting)).thenReturn(artist);
        this.mockMvc.perform(get(urlTemplate + "/" + uuidTesting))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(artist)));
    }

    @Test
    public void updateArtist() throws Exception {
        Artist updatedArtist = Artist.builder()
                .id(uuidTesting)
                .name("Test updated Artist")
                .spotifyId("123456asdasda789789")
                .popularity(69)
                .fetchFlag(false)
                .build();
        when(artistService.updateArtist(uuidTesting, updatedArtist)).thenReturn(updatedArtist);
        this.mockMvc.perform(put(urlTemplate + "/" + uuidTesting)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedArtist)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(updatedArtist)));
    }

    @Test
    public void deleteArtist() throws Exception {
        when(artistService.deleteArtist(uuidTesting)).thenReturn(artist);
        this.mockMvc.perform(delete(urlTemplate + "/" + uuidTesting))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(artist)));
    }

    @Test
    public void createArtist() throws Exception {
        Artist createArtist = Artist.builder()
                .name("Test new Artist")
                .spotifyId("88888444455551123")
                .popularity(1)
                .fetchFlag(false)
                .build();
        Artist finalNewArtist = Artist.builder()
                .id(UUID.randomUUID())
                .name(createArtist.getName())
                .spotifyId(createArtist.getSpotifyId())
                .popularity(createArtist.getPopularity())
                .fetchFlag(createArtist.isFetchFlag())
                .build();
        when(artistService.createArtist(createArtist)).thenReturn(finalNewArtist);
        this.mockMvc.perform(post(urlTemplate + "/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createArtist)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(finalNewArtist)));
    }

    @Test
    public void searchArtists() throws Exception {
        List<Artist> artistList = new ArrayList<>();
        artistList.add(artist);
        when(artistService.searchArtists("Test")).thenReturn(artistList);
        this.mockMvc.perform(get(urlTemplate + "/search")
                        .queryParam("query", "Test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(artistList)));
    }
}
