package edu.bitble.kurse.model.request;

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
public class PaymentRequest {

	private Integer id;
	private Integer userId;
	private Integer enrolmentId;
	private Integer courseId;
	private Integer paymentMethodId;
	private LocalDateTime createdAt;
	private Double coursePrice;
	private Integer appliedVoucherId;
	private Double paymentAmount;
	private PaymentStatus status;

}