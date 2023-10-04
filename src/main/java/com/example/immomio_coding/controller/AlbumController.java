package com.example.immomio_coding.controller;

import com.example.immomio_coding.entities.Album;
import com.example.immomio_coding.services.AlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("albums")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAlbums();
    }

    @GetMapping("/{id}")
    public Album getAlbumById(@PathVariable UUID id) {
        return albumService.getAlbumById(id);
    }

    @PutMapping("/{id}")
    public Album updateAlbum(@PathVariable UUID id, @RequestBody Album updatedAlbum) {
        return albumService.updateAlbum(id, updatedAlbum);
    }

    @DeleteMapping("/{id}")
    public Album deleteAlbum(@PathVariable UUID id) {
        return albumService.deleteAlbum(id);
    }

    @PostMapping("/create")
    public Album createAlbum(@RequestBody Album newAlbum) {
        return albumService.createAlbum(newAlbum);
    }

    @GetMapping("/search")
    public List<Album> searchAlbums(@RequestParam("query") String query) {
        System.out.println("Query param albums: " + query);
        return albumService.searchAlbums(query);
    }
}
