package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.*;
import com.example.feedh.Model.*;
import com.example.feedh.Repostiory.EventParticipantRepository;
import com.example.feedh.Repostiory.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipantService {
    private final EventParticipantRepository eventParticipantRepository;
    private final EventRepository eventRepository;

    public List<EventParticipantDTOout> getAllEventParticipants() {
        List<EventParticipant> eventParticipants = eventParticipantRepository.findAll();
        List<EventParticipantDTOout> eventParticipantDTOS = new ArrayList<>();

        for (EventParticipant ep : eventParticipants) {
            // Customers
            List<CustomerDTOout> customerDTOS = new ArrayList<>();
            for (Customer c : ep.getCustomers()) {
                //  Farms
                List<FarmDTOout> farmDTOS = new ArrayList<>();
                for (Farm f : c.getFarms()) {
                    // Farmers
                    List<FarmerDTOout> farmerDTOS = new ArrayList<>();
                    for (Farmer farmer : f.getFarmers()) {
                        farmerDTOS.add(new FarmerDTOout(farmer.getName(), farmer.getPhoneNumber(), farmer.getAddress(), farmer.getVisaType()));
                    }

                    // Live Stocks
                    List<LiveStockDTOout> liveStockDTOS = new ArrayList<>();
                    for (LiveStock ls : f.getLiveStocks()) {
                        liveStockDTOS.add (new LiveStockDTOout(ls.getType(), ls.getBreed(), ls.getQuantity(), ls.getFeedType()));
                    }

                    // Plants
                    List<PlantDTOout> plantDTOS = new ArrayList<>();
                    for (Plant p : f.getPlants()) {
                        plantDTOS.add(new PlantDTOout(p.getType(), p.getQuantity()));
                    }
                    // Finally adding FarmDTO to the list
                    farmDTOS.add(new FarmDTOout(f.getName(), f.getLocation(), f.getSize(), f.getType(), farmerDTOS, plantDTOS, liveStockDTOS));
                }
                // Adding CustomerDTO to the list
                customerDTOS.add(new CustomerDTOout(c.getName(), c.getEmail(), c.getPhoneNumber(), c.getPhoneNumber(), c.getAddress(), farmDTOS));
            }
            // Adding EventParticipantDTO to the list
            eventParticipantDTOS.add(new EventParticipantDTOout(ep.getStatus(), customerDTOS));
        }
        return eventParticipantDTOS;
    }

    public void addEventParticipant(Integer eventId, EventParticipant eventParticipant) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new ApiException("Event with ID: " + eventId + " was not found");
        }
        eventParticipant.setEvent(event);
        eventParticipantRepository.save(eventParticipant);
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
}
