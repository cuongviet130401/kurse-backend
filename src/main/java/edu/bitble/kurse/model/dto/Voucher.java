package edu.bitble.kurse.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

  	private Integer id;
	private String voucherCode;
	private String voucherTitle;
	private String voucherDescription;
	private double discountRate;
	private double discountAmount;
	private double discountMaxAmount;
	private Integer courseReservation;
	private Integer availableApply;
	private LocalDateTime expiredAt;

}