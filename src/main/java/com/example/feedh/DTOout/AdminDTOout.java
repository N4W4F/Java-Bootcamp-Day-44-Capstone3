package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminDTOout {
    private String name;
    private String email;
    private String phoneNumber;
    private List<EventDTOout> eventDTOouts;
}
