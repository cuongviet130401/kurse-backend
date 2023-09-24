package edu.uit.kurse.kursebackend.model.persistent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "students")
public class Student {

    @Id
    private UUID id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    private Boolean isMale;

    private String avatarUrl;

    private UUID accountId;

    @OneToMany
    private List<Notification> receivedNotificationHistory;

}
