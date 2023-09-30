package com.example.immomio_coding.controller;

import com.example.immomio_coding.entities.Artist;
import com.example.immomio_coding.services.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

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
        try {
            return artistService.createArtist(newArtist);
        } catch (Exception ex) {
            // TODO: fix error handling
            System.out.println("ERRROR FOUND");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Provide correct Actor Id", ex);
        }
    }
}

