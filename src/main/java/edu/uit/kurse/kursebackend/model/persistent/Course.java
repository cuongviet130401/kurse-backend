package edu.uit.kurse.kursebackend.model.persistent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "courses")
public class Course {

    @Id
    private UUID id;

    private String name;

    private String description;

    private String thumbnailUrl;

    private UUID lecturerId;

    private Integer price;

    private LocalDate uploadDate;

    private Boolean isHidden;

    @OneToMany
    private List<Review> reviews;

    @OneToMany
    private List<Topic> topics;

}
