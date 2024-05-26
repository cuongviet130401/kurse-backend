package edu.bitble.kurse.model.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.bitble.kurse.model.LearningStatus;
import edu.bitble.kurse.model.PaymentStatus;
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
public class EnrolmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private Integer userId;

	@NotNull
	private Integer courseId;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LearningStatus learningStatus;

	@NotNull
	private Float learningProgress;

	@NotNull
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime learningStatusLastUpdate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime paymentStatusLastUpdate;

	@CreatedDate
	@Column(name = "createdAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

}