package com.example.immomio_coding.controller;

import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.services.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public Iterable<Artist> getAllArtists() {
        return artistService.getArtists();
    }

    @GetMapping("/{id}")
    public Optional<Artist> getArtistById(@PathVariable String id) {
        return artistService.getArtistById(id);
    }
}
