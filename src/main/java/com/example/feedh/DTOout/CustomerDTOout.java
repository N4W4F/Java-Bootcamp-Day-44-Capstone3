package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// Reemas - Customer DTO
@Getter
@Setter
@AllArgsConstructor
public class CustomerDTOout {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String registerStatus;
    private List<FarmDTOout> farms;
    private List<RentalDTOout> rentals;
}