package de.example.immomio_coding.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(name = "spotify_id", nullable = false, unique = true)
    private String spotifyId;
    private int popularity;
    @Column(name = "fetch_flag")
    private boolean fetchFlag;
}
