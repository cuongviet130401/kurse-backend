package edu.bitble.kurse.model.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "COURSE_RATING_RECORD")
@NoArgsConstructor
@AllArgsConstructor
public class CourseRatingRecordEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private Integer courseId;

	@NotNull
	private Integer userId;

	@CreatedDate
	@Column(name = "createdAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@NotNull
	private Integer rating;

	@NotNull
	private String description;

}