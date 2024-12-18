package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.EventParticipant;
import com.example.feedh.Service.EventParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Nawaf - EventParticipant Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event-participant")
public class EventParticipantController {
    private final EventParticipantService eventParticipantService;
    // CRUD -
    @GetMapping("/get")
    public ResponseEntity getAllEventParticipants() {
        return ResponseEntity.status(200).body(eventParticipantService.getAllEventParticipants());
    }

    @PostMapping("/add/{eventId}/{customerId}")
    public ResponseEntity addEventParticipant(@PathVariable Integer eventId, @PathVariable Integer customerId) {
        eventParticipantService.addEventParticipant(eventId, customerId);
        return ResponseEntity.status(200).body(new ApiResponse("Event Participant has been added to event: " + eventId + " successfully"));
    }

    @PutMapping("/update/{adminId}/{eventParticipantId}")
    public ResponseEntity updateEventParticipant(@PathVariable Integer eventParticipantId, @RequestBody @Valid EventParticipant eventParticipant) {
        eventParticipantService.updateEventParticipant(eventParticipantId, eventParticipant);
        return ResponseEntity.status(200).body(new ApiResponse("Event Participant with ID: " + eventParticipantId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{adminId}/{eventParticipantId}")
    public ResponseEntity deleteEventParticipant(@PathVariable Integer adminId, @PathVariable Integer eventParticipantId) {
        eventParticipantService.deleteEventParticipant(adminId, eventParticipantId);
        return ResponseEntity.status(200).body(new ApiResponse("Event with ID: " + eventParticipantId + " has been deleted successfully"));
    }
}
