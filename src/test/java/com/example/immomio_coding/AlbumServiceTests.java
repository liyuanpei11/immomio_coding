package com.example.immomio_coding;

import com.example.immomio_coding.entities.Album;
import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.repositories.AlbumRepository;
import com.example.immomio_coding.services.AlbumService;
import com.example.immomio_coding.spotify.SpotifyService;
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
public class AlbumServiceTests {
    @Autowired
    private AlbumService albumService;
    @MockBean
    private AlbumRepository albumRepository;
    @MockBean
    private SpotifyService spotifyService;
    static Album album;
    static UUID uuid;
    static Artist artist;

    @BeforeAll
    static void Setup() {
        uuid = UUID.randomUUID();
        artist = Artist.builder()
                .id(UUID.randomUUID())
                .name("Test Artist")
                .spotifyId("845847232556AACCC")
                .popularity(69)
                .fetchFlag(false)
                .build();
        album = Album.builder()
                .id(uuid)
                .name("Test Artist")
                .spotifyId("123456asdasda789789")
                .totalTracks(11)
                .artist(artist)
                .fetchFlag(false)
                .build();
    }

    @Test
    public void getAlbumsTest() {
        List<Album> albumList = new ArrayList<>();
        albumList.add(album);
        albumList.add(Album.builder()
                .id(UUID.randomUUID())
                .name("Test Album 2")
                .spotifyId("8887777AAABBBB")
                .totalTracks(8)
                .artist(artist)
                .fetchFlag(false)
                .build()
        );
        when(albumRepository.findAll()).thenReturn(albumList);
        Assertions.assertFalse(albumService.getAlbums().isEmpty());
        Assertions.assertEquals(albumService.getAlbums().get(0), album);
        Assertions.assertEquals(albumService.getAlbums().get(1).getName(), "Test Album 2");
        Assertions.assertEquals(albumService.getAlbums().get(1).getSpotifyId(), "8887777AAABBBB");
        Assertions.assertEquals(albumService.getAlbums().get(1).getTotalTracks(), 8);
        Assertions.assertEquals(albumService.getAlbums().get(1).getArtist(), artist);
        Assertions.assertFalse(albumService.getAlbums().get(1).isFetchFlag());
    }

    @Test
    public void getAlbumTest() {
        when(albumRepository.findById(uuid)).thenReturn(Optional.ofNullable(album));
        Assertions.assertEquals(albumService.getAlbumById(uuid), album);
    }

    @Test
    public void createAlbumTest() {
        Album newAlbumWithoutUUID = Album.builder()
                .name("Test New Artist")
                .spotifyId("123456asdasda789789")
                .totalTracks(7)
                .artist(artist)
                .fetchFlag(false)
                .build();
        Album newAlbumWithUUID = Album.builder()
                .id(UUID.randomUUID())
                .name(newAlbumWithoutUUID.getName())
                .spotifyId(newAlbumWithoutUUID.getSpotifyId())
                .totalTracks(newAlbumWithoutUUID.getTotalTracks())
                .artist(newAlbumWithoutUUID.getArtist())
                .fetchFlag(newAlbumWithoutUUID.isFetchFlag())
                .build();
        when(albumRepository.save(newAlbumWithoutUUID)).thenReturn(newAlbumWithUUID);
        Assertions.assertEquals(albumService.createAlbum(newAlbumWithoutUUID).getName(), "Test New Artist");
        Assertions.assertEquals(albumService.createAlbum(newAlbumWithoutUUID).getSpotifyId(), "123456asdasda789789");
        Assertions.assertEquals(albumService.createAlbum(newAlbumWithoutUUID).getTotalTracks(), 7);
        Assertions.assertEquals(albumService.createAlbum(newAlbumWithoutUUID).getArtist(), artist);
        Assertions.assertFalse(albumService.createAlbum(newAlbumWithoutUUID).isFetchFlag());
        System.out.println(albumService.createAlbum(newAlbumWithoutUUID));
    }

    @Test
    public void updateAlbumTest() {
        Album updatedAlbum = Album.builder()
                .id(uuid)
                .name("Test updated Artist")
                .spotifyId("778984254546YY")
                .totalTracks(12)
                .artist(artist)
                .fetchFlag(true)
                .build();
        Album finalUpdatedAlbum = Album.builder()
                .id(updatedAlbum.getId())
                .name(updatedAlbum.getName())
                .spotifyId(updatedAlbum.getSpotifyId())
                .totalTracks(updatedAlbum.getTotalTracks())
                .artist(updatedAlbum.getArtist())
                .fetchFlag(false)
                .build();
        when(albumRepository.findById(uuid)).thenReturn(Optional.ofNullable(album));
        Assertions.assertEquals(albumService.updateAlbum(uuid, updatedAlbum), finalUpdatedAlbum);
    }

    @Test
    public void deleteAlbumTest() {
        when(albumRepository.findById(uuid)).thenReturn(Optional.ofNullable(album));
        Assertions.assertEquals(albumService.deleteAlbum(uuid), album);
    }

    @Test
    public void searchAlbumTest() {
        List<Album> albumList = new ArrayList<>();
        albumList.add(album);
        when(albumRepository.searchByContent("Test")).thenReturn(albumList);
        Assertions.assertEquals(1, albumService.searchAlbums("Test").size());
        Assertions.assertEquals(albumService.searchAlbums("Test").get(0), album);
    }
}