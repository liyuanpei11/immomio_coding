package com.example.immomio_coding.services;

import com.example.immomio_coding.dao.AlbumDAO;
import com.example.immomio_coding.entities.Album;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@Service
public class AlbumService {
    private final AlbumDAO albumDAO;

    public AlbumService(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    public Iterable<Album> getAlbums() {
        return albumDAO.findAll();
    }

    public Album getAlbumById(UUID albumId) {
        return albumDAO.findById(albumId).orElseThrow(InvalidParameterException::new);
    }

    public Album createAlbum(Album newAlbum) {
        return albumDAO.save(newAlbum);
    }

    public Album updateAlbum(UUID albumId, Album updatedAlbum) {
        Album currentAlbum = albumDAO.findById(albumId).orElseThrow(InvalidParameterException::new);
        currentAlbum.setName(updatedAlbum.getName());
        currentAlbum.setTotalTracks(updatedAlbum.getTotalTracks());
        currentAlbum.setSpotifyId(updatedAlbum.getSpotifyId());
        currentAlbum.setArtist(updatedAlbum.getArtist());
        currentAlbum.setFetchFlag(false);
        albumDAO.save(currentAlbum);
        return currentAlbum;
    }

    public Album deleteAlbum(UUID albumId) {
        Album deletedAlbum = albumDAO.findById(albumId).orElseThrow(InvalidParameterException::new);
        albumDAO.deleteById(albumId);
        return deletedAlbum;
    }

    public List<Album> searchAlbums(String query) {
        return albumDAO.searchByContent(query);
    }
}
