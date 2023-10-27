package com.example.immomio_coding.spotify;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {
    private final SpotifyArtists spotifyArtists;
    private final SpotifyAlbums spotifyAlbums;

    public SpotifyService(SpotifyArtists spotifyArtists, SpotifyAlbums spotifyAlbums) {
        this.spotifyArtists = spotifyArtists;
        this.spotifyAlbums = spotifyAlbums;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchArtistsList() {
        System.out.println("Start fetching data from spotify");
        List<String> artistsIdList = getArtistsIdList();
        spotifyArtists.fetchSpotifyArtists(artistsIdList);
        for (String artistId : artistsIdList) {
            spotifyAlbums.fetchSpotifyArtistAlbums(artistId);
        }
        System.out.println("Fetching data from spotify done!!!");
    }

    private List<String> getArtistsIdList() {
        List<String> artistsIdList = new ArrayList<>();
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
        return artistsIdList;
    }
}
