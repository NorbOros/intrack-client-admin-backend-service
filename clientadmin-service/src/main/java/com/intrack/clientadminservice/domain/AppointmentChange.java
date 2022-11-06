package com.intrack.clientadminservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentChange {

    private String appointmentId;
    private String desk;
    private String administratorId;
    private AppointmentStatus status;

}
