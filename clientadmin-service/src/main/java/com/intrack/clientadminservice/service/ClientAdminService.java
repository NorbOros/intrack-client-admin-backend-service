package com.intrack.clientadminservice.service;

import com.intrack.clientadminservice.domain.Appointment;
import com.intrack.clientadminservice.domain.AppointmentChange;
import com.intrack.clientadminservice.domain.AppointmentStatus;
import com.intrack.clientadminservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;

@Service
public class ClientAdminService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    private Sinks.Many<Appointment> appointmentSink = Sinks.many().replay().latest();

    public Flux<Appointment> getAppointmentsByStatus(final AppointmentStatus status) {
        return appointmentRepository.findAppointmentByStatus(status.name());
    }

    public Mono<Appointment> changeAppointmentStatus(final AppointmentChange appointmentChange) {
        return fetchAppointmentById(appointmentChange)
                .map(appointment -> changeAppointment(appointment, appointmentChange))
                .flatMap(appointmentRepository::save)
                .doOnNext(appointment -> appointmentSink.tryEmitNext(appointment));
    }

    public Flux<Appointment> streamAppointments() {
        return appointmentSink.asFlux();
    }

    private Appointment changeAppointment(final Appointment appointment, final AppointmentChange appointmentChange) {
        appointment.setStatus(appointmentChange.getStatus());
        appointment.setLastStatusChange(LocalDateTime.now());
        appointment.setDesk(appointmentChange.getDesk());
        appointment.setAdministratorId(appointmentChange.getAdministratorId());

        return appointment;
    }

    private Mono<Appointment> fetchAppointmentById(final AppointmentChange appointmentChange) {
        return appointmentRepository.findById(appointmentChange.getAppointmentId());
    }

}
