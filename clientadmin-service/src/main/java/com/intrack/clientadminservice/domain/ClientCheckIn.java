package com.intrack.clientadminservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ClientCheckIn {

    private String title;
    private String appointmentId;
    private AppointmentStatus status;

}
