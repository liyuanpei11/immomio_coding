package com.example.immomio_coding;

import com.example.immomio_coding.spotify.SpotifyAPIController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ImmomioCodingApplication {

    public static void main(String[] args) {
        System.out.println("i am doing something");
        SpotifyAPIController spotifyAPIController = new SpotifyAPIController();
        spotifyAPIController.getAccessToken();
        SpringApplication.run(ImmomioCodingApplication.class, args);
        System.out.println("i am still doing something lol");
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

}
