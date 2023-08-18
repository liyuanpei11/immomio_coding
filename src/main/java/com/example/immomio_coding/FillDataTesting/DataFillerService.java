package com.example.immomio_coding.FillDataTesting;

import com.example.immomio_coding.dao.AlbumDAO;
import com.example.immomio_coding.dao.ArtistDAO;
import com.example.immomio_coding.entities.Album;
import com.example.immomio_coding.entities.Artist;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataFillerService {
    private AlbumDAO albumDAO;
    private ArtistDAO artistDAO;

    public DataFillerService(AlbumDAO albumDAO, ArtistDAO artistDAO) {
        this.albumDAO = albumDAO;
        this.artistDAO = artistDAO;
    }

    @PostConstruct
    @Transactional
    public void fillData() {
        Artist art1 = new Artist(
                "Fall Out Boy",
                "qwertzuiopasdfghjklxcvbn",
                80
        );

        Artist art2 = new Artist(
                "Lindsey Stirling",
                "lkjhgfdpoiuztmnbvc",
                75
        );

        artistDAO.save(art1);
        artistDAO.save(art2);

        Album alb1 = new Album(
                "FOB Album 1",
                "hgfdfds",
                10,
                art1
        );

        Album alb2 = new Album(
                "Best of Violine",
                "asdasdasdasd",
                8,
                art2
        );

        albumDAO.save(alb1);
        albumDAO.save(alb2);
    }
}
