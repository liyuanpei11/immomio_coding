package com.example.immomio_coding.services;

import com.example.immomio_coding.dao.ArtistDAO;
import com.example.immomio_coding.entities.Artist;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ArtistService {
    private final ArtistDAO artistDAO;

    public ArtistService(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    public Iterable<Artist> getArtists() {
        return artistDAO.findAll();
    }

    public Optional<Artist> getArtistById(String id) {
        return artistDAO.findById(UUID.fromString(id));
    }

    public Artist createArtist(Artist newArtist) {
        return artistDAO.save(newArtist);
    }

    public Artist updateArtist(String artistUUId, Artist updateArtist) {
        Optional<Artist> optionalArtist = artistDAO.findById(UUID.fromString(artistUUId));
        AtomicReference<Artist> dbArtist;
        optionalArtist.ifPresent(artist -> {
            artist.setName(updateArtist.getName());
            artist.setPopularity(updateArtist.getPopularity());
            artist.setSpotifyId(updateArtist.getSpotifyId());
            dbArtist.set(artist);
        });
    }
}
