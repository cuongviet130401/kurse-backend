package edu.bitble.kurse.model.request;

import edu.bitble.kurse.model.LearningStatus;
import edu.bitble.kurse.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrolmentRequest {

	private Integer id;
	private Integer userId;
	private Integer courseId;
	private LearningStatus learningStatus;
	private Float learningProgress;
	private PaymentStatus paymentStatus;
	private LocalDateTime learningStatusLastUpdate;
	private LocalDateTime paymentStatusLastUpdate;
	private LocalDateTime createdAt;

}