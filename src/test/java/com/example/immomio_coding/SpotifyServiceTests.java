package com.example.immomio_coding;

import com.example.immomio_coding.spotify.ScheduledConfig;
import com.example.immomio_coding.spotify.SpotifyAlbums;
import com.example.immomio_coding.spotify.SpotifyArtists;
import com.example.immomio_coding.spotify.SpotifyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;


@SpringBootTest
@SpringJUnitConfig(ScheduledConfig.class)
@ExtendWith(MockitoExtension.class)
public class SpotifyServiceTests {
    @SpyBean
    private SpotifyService spotifyService;
    @MockBean
    private SpotifyArtists spotifyArtists;
    @MockBean
    private SpotifyAlbums spotifyAlbums;

    @Test
    public void fetchSpotifyArtistsTest(){
        await()
                .atMost(90, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(spotifyService, atLeast(2)).fetchArtistsList());
    }

}
