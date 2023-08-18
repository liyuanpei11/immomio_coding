package com.example.immomio_coding.dao;

import com.example.immomio_coding.entities.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArtistDAO extends CrudRepository<Artist, UUID> {}
