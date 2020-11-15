package com.razzies.golden.raspberry.awards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WorstMovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldResponseBeLikeJson() throws Exception {
        mockMvc.perform(get("/api/worst-movies/prizes-range"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getResponse()));
    }

    private String getResponse() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("response.json");
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}