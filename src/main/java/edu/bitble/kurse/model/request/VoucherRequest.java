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
public class VoucherRequest {

	private Integer id;
	private String voucherCode;
	private String voucherTitle;
	private String voucherDescription;
	private Double discountRate;
	private Double discountAmount;
	private Double discountMaxAmount;
	private Integer courseReservation;
	private Integer availableApply;
	private LocalDateTime expiredAt;

}