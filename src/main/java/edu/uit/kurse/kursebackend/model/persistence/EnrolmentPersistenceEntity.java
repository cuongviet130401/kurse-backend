package edu.uit.kurse.kursebackend.model.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uit.kurse.kursebackend.model.LearningStatus;
import edu.uit.kurse.kursebackend.model.PaymentStatus;
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
@Table(name = "ENROLMENT")
@NoArgsConstructor
@AllArgsConstructor
public class EnrolmentPersistenceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private Integer userId;

	@NotNull
	private Integer courseId;

	@NotNull
	private LearningStatus learningStatus;

	@NotNull
	private Float learningProgress;

	@NotNull
	private PaymentStatus paymentStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime learningStatusLastUpdate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime paymentStatusLastUpdate;

	@CreatedDate
	@Column(name = "createdAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

}