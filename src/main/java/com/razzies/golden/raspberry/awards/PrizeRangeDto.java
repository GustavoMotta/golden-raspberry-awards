package com.razzies.golden.raspberry.awards;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PrizeRangeDto {

    private List<PrizeDto> max = new ArrayList<>();

    private List<PrizeDto> min = new ArrayList<>();

}
