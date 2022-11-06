package com.intrack.clientadminservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
@Document(collection = "appointments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Appointment {

    @Id
    private String id;
    private String title;
    private String registrationNm;
    private LocalDateTime dueDateTime;
    private LocalDateTime checkInDateTime;
    private String firstName;
    private String lastName;
    private String desk;
    private AppointmentStatus status;
    private LocalDateTime lastStatusChange;
    private String administratorId;
}
