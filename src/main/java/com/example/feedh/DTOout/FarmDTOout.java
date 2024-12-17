package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FarmDTOout {
    private String name;
    private String location;
    private Double size;
    private String type;
    private List<FarmerDTOout> farmers;
    private List<PlantDTOout> plants;
    private List<LiveStockDTOout> liveStocks;
}