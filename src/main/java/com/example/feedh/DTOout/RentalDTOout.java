package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RentalDTOout {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Double price;
    private String status;
}
