package com.intrack.clientadminservice.controller;

import com.intrack.clientadminservice.domain.Appointment;
import com.intrack.clientadminservice.domain.AppointmentChange;
import com.intrack.clientadminservice.domain.AppointmentStatus;
import com.intrack.clientadminservice.service.ClientAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/client-admin/appointments")
public class ClientAdminController {

    @Autowired
    private ClientAdminService clientAdminService;

    @GetMapping(value = "/{status}")
    public ResponseEntity<Flux<Appointment>> getAppointmentsByStatus(@PathVariable("status") final AppointmentStatus status) {
        return ResponseEntity.ok()
                             .body(clientAdminService.getAppointmentsByStatus(status));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Appointment>> changeAppointmentStatus(@RequestBody final AppointmentChange appointmentChange) {
        return clientAdminService.changeAppointmentStatus(appointmentChange)
                                 .map(ResponseEntity.ok()::body);

    }


    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Appointment>> streamCalledClients() {
        return clientAdminService.streamAppointments()
                                 .map(appointment -> ServerSentEvent.<Appointment>builder()
                                                                    .id(appointment.getId())
                                                                    .event(appointment.getStatus().name())
                                                                    .data(appointment)
                                                                    .build());
    }
}

