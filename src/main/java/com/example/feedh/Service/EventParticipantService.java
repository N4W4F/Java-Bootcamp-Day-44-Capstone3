package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.*;
import com.example.feedh.Model.*;
import com.example.feedh.Repository.CustomerRepository;
import com.example.feedh.Repository.EventParticipantRepository;
import com.example.feedh.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipantService {
    private final EventParticipantRepository eventParticipantRepository;
    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;
    // CRUD - Start
    public List<EventParticipantDTOout> getAllEventParticipants() {
        List<EventParticipant> participants = eventParticipantRepository.findAll();
        List<EventParticipantDTOout> participantDTOS = new ArrayList<>();

        for (EventParticipant participant : participants) {
            // Map Customer to CustomerDTO
            CustomerDTOout customerDTOout = new CustomerDTOout(
                    participant.getCustomer().getName(),
                    participant.getCustomer().getEmail(),
                    participant.getCustomer().getPhoneNumber(),
                    participant.getCustomer().getAddress(),
                    participant.getCustomer().getRegisterStatus(),
                    null, // Assuming farms aren't needed for this response
                    null  // Assuming rentals aren't needed for this response
            );

            // Map Event to EventDTO
            EventDTOout eventDTOout = new EventDTOout(
                    participant.getEvent().getName(),
                    participant.getEvent().getDescription(),
                    participant.getEvent().getLocation(),
                    participant.getEvent().getStartDateTime(),
                    participant.getEvent().getEndDateTime(),
                    participant.getEvent().getStatus()
            );

            // Add to the DTO list
            participantDTOS.add(new EventParticipantDTOout(
                    participant.getStatus(),
                    customerDTOout,
                    eventDTOout
            ));
        }

        return participantDTOS;
    }


    public void addEventParticipant(Integer eventId, Integer customerId) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new ApiException("Event with ID: " + eventId + " was not found");
        }
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }
        EventParticipant participant = new EventParticipant();
        participant.setCustomer(customer);
        participant.setEvent(event);
        eventParticipantRepository.save(participant);
    }


    public void updateEventParticipant(Integer eventParticipantId, EventParticipant eventParticipant) {
        EventParticipant oldEventParticipant = eventParticipantRepository.findEventParticipantById(eventParticipantId);
        if (oldEventParticipant == null) {
            throw new ApiException("Event Participant with ID: " + eventParticipantId + " was not found");
        }
        oldEventParticipant.setStatus(eventParticipant.getStatus());
    }

    public void deleteEventParticipant(Integer eventParticipantId) {
        EventParticipant eventParticipant = eventParticipantRepository.findEventParticipantById(eventParticipantId);
        if (eventParticipant == null) {
            throw new ApiException("Event Participant with ID: " + eventParticipantId + " was not found");
        }
        eventParticipantRepository.delete(eventParticipant);
    }
    // CRUD - End

    // Getters

    // Services
}
