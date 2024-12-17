package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.EventDTOout;
import com.example.feedh.Model.Admin;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.Event;
import com.example.feedh.Repository.AdminRepository;
import com.example.feedh.Repository.CustomerRepository;
import com.example.feedh.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    // CRUD - Start
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

    public void deleteEvent(Integer adminId ,Integer eventId) {
        Admin admin=adminRepository.findAdminById(adminId);
        Event event = eventRepository.findEventById(eventId);
        if (event == null||admin==null) {
            throw new ApiException("event not found");
        }
        eventRepository.delete(event);
    }
    // CRUD - End

    // ************
    // end poind get event

    public List<Event> getEventByDate(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)){
            throw new ApiException("start date must be before end date");
        }
        return eventRepository.findEventByStartDateTimeBetween(start, end);

    }

   //// Event By Location And Date
    public List<Event>getEventByLocationAndDate(Integer customer_id,String location, LocalDateTime startDate, LocalDateTime endDateTime){
        Customer customer=customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customer_id + " was not found");
        }
        List<Event>eventList=eventRepository.getEventByLocationAndStartDateAndEndDateTime(location, startDate, endDateTime);

        if(eventList.isEmpty()){
            throw new ApiException("Sorry look like there’s no event like this details");
        }
        return eventList;

    }

}
