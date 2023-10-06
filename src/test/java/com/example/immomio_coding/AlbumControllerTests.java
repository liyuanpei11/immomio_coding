package com.example.immomio_coding;

import com.example.immomio_coding.controller.AlbumController;
import com.example.immomio_coding.entities.Album;
import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.services.AlbumService;
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

@WebMvcTest(AlbumController.class)
public class AlbumControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumService albumService;
    @MockBean
    private SpotifyService spotifyService;

    static Artist artist;
    static Album album;
    static UUID uuidTesting;
    static ObjectMapper objectMapper;
    static String urlTemplate = "/albums";

    @BeforeAll
    public static void beforeTesting() {
        uuidTesting = UUID.randomUUID();
        artist = Artist.builder()
                .id(UUID.randomUUID())
                .name("Test Artist")
                .spotifyId("123456asdasda789789")
                .popularity(69)
                .fetchFlag(false)
                .build();
        album = Album.builder()
                .id(uuidTesting)
                .name("Test Album")
                .spotifyId("456456564465645aasdasdasd")
                .totalTracks(13)
                .artist(artist)
                .fetchFlag(false)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAlbums() throws Exception {
        List<Album> albumList = new ArrayList<>();
        albumList.add(album);
        albumList.add(Album.builder()
                .id(UUID.randomUUID())
                .name("Test Album 2")
                .spotifyId("99988777DDD555444")
                .totalTracks(55)
                    .artist(Artist.builder()
                            .id(UUID.randomUUID())
                            .name("Test Artist 2")
                            .spotifyId("333344445555sss")
                            .popularity(1)
                            .fetchFlag(true)
                            .build())
                .fetchFlag(true)
                .build());
        when(albumService.getAlbums()).thenReturn(albumList);
        this.mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(albumList)));
    }

    @Test
    public void getAlbum() throws Exception {
        when(albumService.getAlbumById(uuidTesting)).thenReturn(album);
        this.mockMvc.perform(get(urlTemplate + "/" + uuidTesting))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(album)));
    }

    @Test
    public void updateArtist() throws Exception {
        Album updatedAlbum = Album.builder()
                .id(uuidTesting)
                .name("Test Album 2")
                .spotifyId("456456564465645aasdasdasd")
                .totalTracks(13)
                .artist(artist)
                .fetchFlag(false)
                .build();
        when(albumService.updateAlbum(uuidTesting, updatedAlbum)).thenReturn(updatedAlbum);
        this.mockMvc.perform(put(urlTemplate + "/" + uuidTesting)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedAlbum)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(updatedAlbum)));
    }

    @Test
    public void deleteAlbum() throws Exception {
        when(albumService.deleteAlbum(uuidTesting)).thenReturn(album);
        this.mockMvc.perform(delete(urlTemplate + "/" + uuidTesting))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(album)));
    }

    @Test
    public void createAlbum() throws Exception {
        Album createdAlbum = Album.builder()
                .name("Test New Album")
                .spotifyId("55556666ZZZZ111")
                .totalTracks(66)
                .artist(artist)
                .fetchFlag(false)
                .build();
        Album finalNewAlbum = Album.builder()
                .id(UUID.randomUUID())
                .name(createdAlbum.getName())
                .spotifyId(createdAlbum.getSpotifyId())
                .totalTracks(createdAlbum.getTotalTracks())
                .artist(createdAlbum.getArtist())
                .fetchFlag(createdAlbum.isFetchFlag())
                .build();
        when(albumService.createAlbum(createdAlbum)).thenReturn(finalNewAlbum);
        this.mockMvc.perform(post(urlTemplate + "/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createdAlbum)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(finalNewAlbum)));
    }

    @Test
    public void searchAlbums() throws Exception {
        List<Album> albumList = new ArrayList<>();
        albumList.add(album);
        when(albumService.searchAlbums("Test")).thenReturn(albumList);
        this.mockMvc.perform(get(urlTemplate + "/search")
                        .queryParam("query", "Test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(albumList)));
    }
}
