package com.example.immomio_coding.services;

import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getArtists() {
        return artistRepository.findAll();
    }

    public Artist getArtistById(UUID artistId) {
        return artistRepository.findById(artistId).orElseThrow();
    }

    public Artist createArtist(Artist newArtist) {
        return artistRepository.save(newArtist);
    }

    public Artist updateArtist(UUID artistUUId, Artist updateArtist) {
        Artist currentArtist = artistRepository.findById(artistUUId).orElseThrow();
        currentArtist.setName(updateArtist.getName());
        currentArtist.setPopularity(updateArtist.getPopularity());
        currentArtist.setSpotifyId(updateArtist.getSpotifyId());
        currentArtist.setFetchFlag(false);
        artistRepository.save(currentArtist);
        return currentArtist;
    }

    public Artist deleteArtist(UUID artistUUID) {
        Artist deletedArtist = artistRepository.findById(artistUUID).orElseThrow();
        artistRepository.deleteById(artistUUID);
        return deletedArtist;
    }

    public List<Artist> searchArtists(String query) {
        return artistRepository.searchByContent(query);
    }
}
