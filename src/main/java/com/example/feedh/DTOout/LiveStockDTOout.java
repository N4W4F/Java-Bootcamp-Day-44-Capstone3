package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LiveStockDTOout {
    private String type;
    private String breed;
    private Integer quantity;
    private String feedType;
}
