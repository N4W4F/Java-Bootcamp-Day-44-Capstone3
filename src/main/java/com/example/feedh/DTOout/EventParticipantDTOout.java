package com.example.feedh.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventParticipantDTOout {
    private String status;
    private CustomerDTOout customerDTOout;
    private EventDTOout eventDTOout;
}
