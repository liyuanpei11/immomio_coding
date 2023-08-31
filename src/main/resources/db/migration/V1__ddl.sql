CREATE TABLE album
(
    id              UUID NOT NULL,
    name            VARCHAR(255),
    spotify_id      VARCHAR(255),
    total_tracks    INT,
    artist_id       UUID,
    fetch_flag      BOOlEAN,
    PRIMARY KEY (id)
);

CREATE TABLE artist
(
    id              UUID NOT NULL,
    name            VARCHAR(255),
    spotify_id      VARCHAR(255),
    popularity      INT,
    fetch_flag      BOOlEAN,
    PRIMARY KEY (id)
);

ALTER TABLE album
    ADD CONSTRAINT fk_album_artist FOREIGN KEY (artist_id) REFERENCES artist;