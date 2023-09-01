package com.example.immomio_coding.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album extends EntityWithUUID {
    private String name;
    @Column(name = "spotify_id", nullable = false, unique = true)
    private String spotifyId;
    @Column(name = "total_tracks")
    private int totalTracks;
    @Column(name = "fetch_flag")
    private boolean fetchFlag;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_album_artist"))
    private Artist artist;

}
