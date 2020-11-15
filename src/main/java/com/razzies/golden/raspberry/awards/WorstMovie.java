package com.razzies.golden.raspberry.awards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "WORST_MOVIES")
@NoArgsConstructor
@JsonPropertyOrder({"year", "title", "studios", "producers", "winner"})
public class WorstMovie {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STUDIOS")
    private String studios;

    @Column(name = "PRODUCERS")
    private String producers;

    @Column(name = "WINNER")
    private String winner;
}
