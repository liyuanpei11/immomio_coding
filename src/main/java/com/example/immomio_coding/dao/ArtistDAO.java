package com.example.immomio_coding.dao;

import com.example.immomio_coding.entities.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ArtistDAO extends CrudRepository<Artist, UUID> {
    Artist findBySpotifyId(String spotifyId);

    @Query(
            value = "SELECT a FROM Artist a WHERE to_tsvector('english', a.name) @@ to_tsquery('english', :query)",
            nativeQuery = true)
    List<Artist> searchByContent(@Param("query") String query);
}
