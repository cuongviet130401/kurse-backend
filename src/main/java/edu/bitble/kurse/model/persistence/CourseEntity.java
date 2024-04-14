package edu.bitble.kurse.model.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "COURSE")
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private String title;

	@NotNull
	private String subtitle;

	@CreatedDate
	@Column(name = "createdAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime lastUpdatedAt;

	@NotNull
	private String bannerUrl;

	@NotNull
	private String thumbnailUrl;

	@NotNull
	private String introductoryVideoUrl;

	@NotNull
	private String courseCurriculumRefId;

	@NotNull
	private String courseResourceRefId;

	@NotNull
	private String description;

	@NotNull
	private double price;

	@NotNull
	private Integer numberOfEnrolled;

	@NotNull
	private Integer numberOfLiked;

}