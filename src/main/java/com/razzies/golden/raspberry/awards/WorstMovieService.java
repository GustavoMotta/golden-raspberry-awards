package com.razzies.golden.raspberry.awards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WorstMovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorstMovieService.class);

    private static final String COMMA_SEPARATOR = ", ";
    private static final String AND_SEPARATOR = " and ";

    private final WorstMovieRepository repository;

    public WorstMovieService(WorstMovieRepository repository) {
        this.repository = repository;
    }

    public PrizeRangeDto getPrizeRange() {
        Map<String, List<WorstMovie>> producerMoviesGroup = getProducerMoviesGroup();
        PrizeRangeDto dto = new PrizeRangeDto();
        producerMoviesGroup.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(entry -> {
                    addMax(dto.getMax(), entry);
                    addMin(dto.getMin(), entry);
                });
        return dto;
    }

    private void addMin(List<PrizeDto> min, Map.Entry<String, List<WorstMovie>> entry) {
        List<WorstMovie> movies = entry.getValue();
        PrizeDto prize = null;
        if (!min.isEmpty()) {
            prize = min.get(0);
        }
        Iterator<WorstMovie> iterator = movies.iterator();
        WorstMovie last = iterator.next();
        while (iterator.hasNext()) {
            WorstMovie current = iterator.next();
            PrizeDto newPrize = createPrize(entry, last, current);
            if (iterator.hasNext()) {
                last = current;
            }
            if (prize != null && prize.getInterval() > newPrize.getInterval()) {
                prize = newPrize;
                continue;
            }
            if (prize == null || Objects.equals(prize.getInterval(), newPrize.getInterval())) {
                prize = newPrize;
                min.add(prize);
                continue;
            }
            prize = newPrize;
            min.clear();
            min.add(prize);
        }
    }

    private void addMax(List<PrizeDto> max, Map.Entry<String, List<WorstMovie>> entry) {
        List<WorstMovie> movies = entry.getValue();
        PrizeDto prize = null;
        if (!max.isEmpty()) {
            prize = max.get(0);
        }
        Iterator<WorstMovie> iterator = movies.iterator();
        WorstMovie last = iterator.next();
        while (iterator.hasNext()) {
            WorstMovie current = iterator.next();
            PrizeDto newPrize = createPrize(entry, last, current);
            if (iterator.hasNext()) {
                last = current;
            }
            if (prize != null && prize.getInterval() < newPrize.getInterval()) {
                prize = newPrize;
                continue;
            }
            if (prize == null || Objects.equals(prize.getInterval(), newPrize.getInterval())) {
                prize = newPrize;
                max.add(prize);
                continue;
            }
            prize = newPrize;
            max.clear();
            max.add(prize);
        }
    }

    private PrizeDto createPrize(Map.Entry<String, List<WorstMovie>> entry, WorstMovie last, WorstMovie current) {
        PrizeDto newPrize = new PrizeDto();
        newPrize.setProducer(entry.getKey());
        newPrize.setPreviousWin(last.getYear());
        newPrize.setFollowingWin(current.getYear());
        newPrize.setInterval(current.getYear() - last.getYear());
        return newPrize;
    }

    private Map<String, List<WorstMovie>> getProducerMoviesGroup() {
        LOGGER.info("Grouping winner movies by producer");
        List<WorstMovie> movies = repository.findAllByWinner("yes");
        Map<String, List<WorstMovie>> producerMoviesGroup = new HashMap<>();
        movies.forEach(movie -> group(producerMoviesGroup, movie));
        return producerMoviesGroup;
    }

    private void group(Map<String, List<WorstMovie>> producerMoviesGroup, WorstMovie movie) {
        if (movie.getProducers().contains(COMMA_SEPARATOR) || movie.getProducers().contains(AND_SEPARATOR)) {
            List<String> producers = getSplittedProducers(movie.getProducers());
            producers.forEach(producer -> {
                producerMoviesGroup.putIfAbsent(producer, new ArrayList<>());
                producerMoviesGroup.get(producer).add(movie);
            });
            return;
        }
        producerMoviesGroup.putIfAbsent(movie.getProducers(), new ArrayList<>());
        producerMoviesGroup.get(movie.getProducers()).add(movie);
    }

    private List<String> getSplittedProducers(String producers) {
        return Stream.of(producers.split(COMMA_SEPARATOR))
                .flatMap(producer -> Stream.of(producer.split(AND_SEPARATOR)))
                .collect(Collectors.toList());
    }
}
