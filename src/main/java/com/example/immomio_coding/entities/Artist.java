package com.example.immomio_coding.entities;

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
    private String spotify_id;
    private int popularity;
}
