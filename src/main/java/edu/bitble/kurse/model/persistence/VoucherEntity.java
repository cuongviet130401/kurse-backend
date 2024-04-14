package edu.bitble.kurse.model.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "VOUCHER")
@NoArgsConstructor
@AllArgsConstructor
public class VoucherEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private String voucherCode;

	@NotNull
	private String voucherTitle;

	@NotNull
	private String voucherDescription;

	@NotNull
	private double discountRate;

	@NotNull
	private double discountAmount;

	@NotNull
	private double discountMaxAmount;

	@NotNull
	private Integer courseReservation;

	@NotNull
	private Integer availableApply;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime expiredAt;

}