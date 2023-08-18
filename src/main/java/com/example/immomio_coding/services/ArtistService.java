package com.example.immomio_coding.services;

import com.example.immomio_coding.dao.ArtistDAO;
import com.example.immomio_coding.entities.Artist;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
}
