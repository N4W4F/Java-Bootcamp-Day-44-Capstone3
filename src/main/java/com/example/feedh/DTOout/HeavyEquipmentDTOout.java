package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HeavyEquipmentDTOout {
    private String name;
    private Double pricePerHour;
    private Double insurance;
    private String status;
}
