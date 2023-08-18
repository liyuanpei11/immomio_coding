package com.example.immomio_coding.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album extends EntityWithUUID {
    private String name;
    private String spotify_id;
    private int total_tracks;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_album_artist"))
    private Artist artist;

}
