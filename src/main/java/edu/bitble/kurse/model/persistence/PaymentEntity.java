package edu.bitble.kurse.model.persistence;

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
@Table(name = "PAYMENT")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private Integer userId;

	@NotNull
	private Integer enrolmentId;

	@NotNull
	private Integer courseId;

	@NotNull
	private Integer paymentMethodId;

	@CreatedDate
	@Column(name = "createdAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@NotNull
	private double coursePrice;

	@NotNull
	private Integer appliedVoucherId;

	@NotNull
	private double paymentAmount;

	@NotNull
	private PaymentStatus status;

}