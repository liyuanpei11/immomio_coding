package com.example.immomio_coding.repositories;

import com.example.immomio_coding.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    Optional<Artist> findBySpotifyId(String spotifyId);

    @Query(
            value = "SELECT * FROM Artist WHERE to_tsvector(" +
                    "name || ' ' || spotify_id || ' ' || popularity || ' ' || fetch_flag" +
                    ") @@ to_tsquery(:query)",
            nativeQuery = true)
    List<Artist> searchByContent(@Param("query") String query);
}
