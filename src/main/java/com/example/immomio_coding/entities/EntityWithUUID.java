package com.example.immomio_coding.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.UUIDJdbcType;

import java.util.UUID;

@MappedSuperclass
public class EntityWithUUID {
    @Id
    @JdbcType(UUIDJdbcType.class)
    private UUID id;

    public EntityWithUUID() {
        this.id = UUID.randomUUID();
    }
}
