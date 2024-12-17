package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTOout {
    private String name;
    private String category;
    private String description;
    private Double price;
}
