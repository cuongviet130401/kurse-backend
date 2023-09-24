package edu.uit.kurse.kursebackend.model.persistent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "appointments")
public class Appointment {

    @Id
    private UUID id;

    private UUID studentId;

    private UUID lecturersId;

    private LocalDateTime suggestionDateTime;

    private LocalDateTime finalDateTime;

    private String title;

    private String description;

    private AppointmentStatus appointmentStatus;

    private String onlineConferenceUrl;

    private String location;

    enum AppointmentStatus {
        OPENED,
        CONFIRMED,
        POSTPONED,
        CANCELLED
    }

}
