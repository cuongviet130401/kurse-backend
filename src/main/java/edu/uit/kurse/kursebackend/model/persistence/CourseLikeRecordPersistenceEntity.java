package edu.uit.kurse.kursebackend.model.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uit.kurse.kursebackend.model.AccountRole;
import edu.uit.kurse.kursebackend.model.OAuth2Provider;
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
@Table(name = "COURSE_LIKE_RECORD")
@NoArgsConstructor
@AllArgsConstructor
public class CourseLikeRecordPersistenceEntity {

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

}