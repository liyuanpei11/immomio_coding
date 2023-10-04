package com.example.immomio_coding.dao;

import com.example.immomio_coding.entities.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AlbumDAO extends CrudRepository<Album, UUID> {

    Album findBySpotifyId(String spotifyId);

    @Query(
            value = "SELECT * FROM Album WHERE to_tsvector(" +
                    "name || ' ' || spotify_id || ' ' || total_tracks || ' ' || artist_id || ' ' || fetch_flag" +
                    ") @@ to_tsquery(:query)",
            nativeQuery = true)
    List<Album> searchByContent(@Param("query") String query);
}
