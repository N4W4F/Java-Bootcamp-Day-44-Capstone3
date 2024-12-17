package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.EventDTOout;
import com.example.feedh.Model.Admin;
import com.example.feedh.Model.Event;
import com.example.feedh.Repostiory.AdminRepository;
import com.example.feedh.Repostiory.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AdminRepository adminRepository;

    public List<EventDTOout> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTOout> eventDTOS = new ArrayList<>();

        for (Event e : events) {
            eventDTOS.add(new EventDTOout(e.getName(), e.getDescription(), e.getLocation(), e.getStartDateTime(), e.getEndDateTime(), e.getStatus()));
        }
        return eventDTOS;
    }

    public void addEvent(Integer adminId, Event event) {
        Admin admin = adminRepository.findAdminById(adminId);
        if (admin == null) {
            throw new ApiException("Admin with ID: " + adminId + " was not found");
        }
        event.setAdmin(admin);
        eventRepository.save(event);
    }

    public void updateEvent(Integer eventId, Event event) {
        Event oldEvent = eventRepository.findEventById(eventId);
        if (oldEvent == null) {
            throw new ApiException("Event with ID: " + eventId + " was not found");
        }
        oldEvent.setName(event.getName());
        oldEvent.setDescription(event.getDescription());
        oldEvent.setLocation(event.getLocation());
        oldEvent.setStartDateTime(event.getStartDateTime());
        oldEvent.setEndDateTime(event.getEndDateTime());
        oldEvent.setStatus(event.getStatus());
        eventRepository.save(oldEvent);
    }

    public void deleteEvent(Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new ApiException("Event with ID: " + eventId + " was not found");
        }
        eventRepository.delete(event);
    }
}
