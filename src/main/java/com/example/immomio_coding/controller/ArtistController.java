package com.example.immomio_coding.controller;

import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.services.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getArtists();
    }

    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable UUID id) {
        return artistService.getArtistById(id);
    }

    @PutMapping("/{id}")
    public Artist updateArtist(@PathVariable UUID id, @RequestBody Artist updatedArtist) {
        return artistService.updateArtist(id, updatedArtist);
    }

    @DeleteMapping("/{id}")
    public Artist deleteArtist(@PathVariable UUID id) {
        return artistService.deleteArtist(id);
    }

    @PostMapping("/create")
    public Artist createArtist(@RequestBody Artist newArtist) {
        return artistService.createArtist(newArtist);
    }

    @GetMapping("/search")
    public List<Artist> searchArtists(@RequestParam("query") String query) {
        return artistService.searchArtists(query);
    }
}

