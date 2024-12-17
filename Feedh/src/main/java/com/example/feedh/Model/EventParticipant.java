package com.example.feedh.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EventParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(50) DEFAULT ('Pending')")
    @Size(min = 1, max = 50, message = "Event Participant Status must be between 1 and 50 characters")
    @NotEmpty(message = "Event Participant Status cannot be empty")
    @Pattern(regexp = "^(Pending|Accepted|Rejected)$",
            message = "Event Participant Status must be either 'Pending', 'Accepted', or 'Rejected'")
    private String status = "Pending";

    //
    @ManyToOne
    @JsonIgnore
    private Event event;

    @ManyToMany(mappedBy = "eventParticipants")
    private Set<Customer> customers;
}