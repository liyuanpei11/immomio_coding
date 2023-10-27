package de.example.immomio_coding.spotify;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("de.example.immomio_coding.spotify")
public class ScheduledConfig {
}
