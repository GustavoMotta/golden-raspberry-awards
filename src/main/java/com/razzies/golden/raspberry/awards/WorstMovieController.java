package com.razzies.golden.raspberry.awards;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/worst-movies")
public class WorstMovieController {

    private final WorstMovieService worstMovieService;

    public WorstMovieController(WorstMovieService worstMovieService) {
        this.worstMovieService = worstMovieService;
    }

    @GetMapping("/prizes-range")
    public ResponseEntity<PrizeRangeDto> getRangePrizes() {
        return ResponseEntity.ok(worstMovieService.getPrizeRange());
    }
}
