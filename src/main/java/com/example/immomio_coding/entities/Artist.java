package com.example.immomio_coding.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist extends EntityWithUUID {
    private String name;
    @Column(name = "spotify_id", nullable = false, unique = true)
    private String spotifyId;
    private int popularity;
    @Column(name = "fetch_flag")
    private boolean fetchFlag;
}
