package com.razzies.golden.raspberry.awards;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrizeDto {

    private String producer;

    private Integer interval;

    private Integer previousWin;

    private Integer followingWin;

}