package com.razzies.golden.raspberry.awards;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorstMovieRepository extends CrudRepository<WorstMovie, Long> {

    List<WorstMovie> findAllByWinner(String winner);

}
