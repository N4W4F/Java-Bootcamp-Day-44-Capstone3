package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.Event;
import com.example.feedh.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {
  private final EventService eventService;

  @GetMapping("/get")
  public ResponseEntity getAll(){
      return ResponseEntity.status(200).body(eventService.getAllEvents());
  }


  @PostMapping("/add/{adminId}")
  public ResponseEntity addNewEvent(@PathVariable Integer adminId, @RequestBody @Valid Event event){
      eventService.addEvent(adminId,event);
      return ResponseEntity.status(200).body(new ApiResponse("add event success"));
  }

  @PutMapping("/update/{eventId}")
  public ResponseEntity updateEvent(@PathVariable Integer eventId,@RequestBody @Valid Event event){
      eventService.updateEvent(eventId, event);
      return ResponseEntity.status(200).body(new ApiResponse(" update event success"));

  }


@DeleteMapping("/delete/admin/{admainId}/event/{eventId}")
  public ResponseEntity Delete(@PathVariable Integer admainId,@PathVariable Integer eventId){
      eventService.deleteEvent(admainId,eventId);
      return ResponseEntity.status(200).body(new ApiResponse("delete event success"));

  }


  //**********end point******************
  @GetMapping("/date/start/{start}/end/{end}")
  public ResponseEntity getDate(@PathVariable LocalDateTime start,@PathVariable LocalDateTime end){
      List<Event> events=eventService.getEventByDate(start, end);
      return ResponseEntity.status(200).body(events);

  }

  @GetMapping("/getEventByLocationAndDate")
  public ResponseEntity getEventByLocationAndDate(@RequestBody Integer customer_id,@RequestBody String location, @RequestBody LocalDateTime startDate
          ,@RequestBody LocalDateTime endDateTime){
      List<Event>events=eventService.getEventByLocationAndDate(customer_id, location, startDate, endDateTime);
      return ResponseEntity.status(200).body(events);

  }


}
