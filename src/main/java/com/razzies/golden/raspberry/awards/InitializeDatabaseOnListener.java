package com.razzies.golden.raspberry.awards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitializeDatabaseOnListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeDatabaseOnListener.class);

    private final CsvFileReader reader;

    private final WorstMovieRepository repository;

    public InitializeDatabaseOnListener(CsvFileReader reader, WorstMovieRepository repository) {
        this.reader = reader;
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doPrepareDatabase() {
        List<WorstMovie> movies = reader.read(WorstMovie.class, "movielist.csv");
        LOGGER.info("Starting create database data");
        movies.forEach(repository::save);
        LOGGER.info("Inserted {} data", movies.size());
    }
}
