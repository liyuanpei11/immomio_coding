package com.example.immomio_coding.repositories;

import com.example.immomio_coding.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {

    Optional<Album> findBySpotifyId(String spotifyId);

    @Query(
            value = "SELECT * FROM Album WHERE to_tsvector(" +
                    "name || ' ' || spotify_id || ' ' || total_tracks || ' ' || artist_id || ' ' || fetch_flag" +
                    ") @@ to_tsquery(:query)",
            nativeQuery = true)
    List<Album> searchByContent(@Param("query") String query);
}
