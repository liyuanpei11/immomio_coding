package com.example.immomio_coding.services;

import com.example.immomio_coding.dao.ArtistDAO;
import com.example.immomio_coding.entities.Artist;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
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

    public Artist getArtistById(UUID artistId) {
        return artistDAO.findById(artistId).orElseThrow(InvalidParameterException::new);
    }

    public Artist createArtist(Artist newArtist) {
        return artistDAO.save(newArtist);
    }

    public Artist updateArtist(UUID artistUUId, Artist updateArtist) {
        Artist currentArtist = artistDAO.findById(artistUUId).orElseThrow(InvalidParameterException::new);
        currentArtist.setName(updateArtist.getName());
        currentArtist.setPopularity(updateArtist.getPopularity());
        currentArtist.setSpotifyId(updateArtist.getSpotifyId());
        currentArtist.setFetchFlag(false);
        artistDAO.save(currentArtist);
        return currentArtist;
    }

    public Artist deleteArtist(UUID artistUUID) {
        Artist deletedArtist = artistDAO.findById(artistUUID).orElseThrow(InvalidParameterException::new);
        artistDAO.deleteById(artistUUID);
        return deletedArtist;
    }

    public List<Artist> searchArtists(String query) {
        return artistDAO.searchByContent(query);
    }
}
