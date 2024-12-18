package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

// Nawaf - EventParticipant DTO
@Data
@AllArgsConstructor
public class EventParticipantDTOout {
    private String status;
    private CustomerDTOout customerDTOout;
    private EventDTOout eventDTOout;
}
