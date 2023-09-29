package com.example.immomio_coding.dao;

import com.example.immomio_coding.entities.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AlbumDAO extends CrudRepository<Album, UUID> {

    Album findBySpotifyId(String spotifyId);
}
