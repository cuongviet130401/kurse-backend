package edu.bitble.kurse.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

	private Integer id;
	private String title;
	private String subtitle;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;
	private String bannerUrl;
	private String thumbnailUrl;
	private String introductoryVideoUrl;
	private String courseCurriculumRefId;
	private String courseResourceRefId;
	private String description;
	private Double price;
	private Integer numberOfEnrolled;
	private Integer numberOfLiked;

}