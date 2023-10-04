package com.example.immomio_coding.services;

import com.example.immomio_coding.entities.Album;
import com.example.immomio_coding.repositories.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(UUID albumId) {
        return albumRepository.findById(albumId).orElseThrow();
    }

    public Album createAlbum(Album newAlbum) {
        return albumRepository.save(newAlbum);
    }

    public Album updateAlbum(UUID albumId, Album updatedAlbum) {
        Album currentAlbum = albumRepository.findById(albumId).orElseThrow();
        currentAlbum.setName(updatedAlbum.getName());
        currentAlbum.setTotalTracks(updatedAlbum.getTotalTracks());
        currentAlbum.setSpotifyId(updatedAlbum.getSpotifyId());
        currentAlbum.setArtist(updatedAlbum.getArtist());
        currentAlbum.setFetchFlag(false);
        albumRepository.save(currentAlbum);
        return currentAlbum;
    }

    public Album deleteAlbum(UUID albumId) {
        Album deletedAlbum = albumRepository.findById(albumId).orElseThrow();
        albumRepository.deleteById(albumId);
        return deletedAlbum;
    }

    public List<Album> searchAlbums(String query) {
        return albumRepository.searchByContent(query);
    }
}
