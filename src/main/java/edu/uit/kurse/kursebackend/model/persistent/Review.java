package edu.uit.kurse.kursebackend.model.persistent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "course_review")
public class Review {

    @Id
    private UUID id;

    private UUID courseId;

    private UUID studentId;

    private Float rating;

    private String title;

    private String description;

}
