package com.intrack.clientadminservice.repository;


import com.intrack.clientadminservice.domain.Appointment;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AppointmentRepository extends ReactiveMongoRepository<Appointment, String> {

    @Query(value = "{status : ?0}")
    Flux<Appointment> findAppointmentByStatus(String status);
}
