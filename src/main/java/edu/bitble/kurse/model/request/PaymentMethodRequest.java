package edu.bitble.kurse.model.request;

import edu.bitble.kurse.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodRequest {

	private Integer id;
	private Integer userId;
	private PaymentType paymentType;
	private String bankCode;
	private String bankAccount;
	private String cardNumber;
	private String cardHolderName;
	private String expiredDate;
	private String billingAddress1;
	private String billingAddress2;
	private String billingAddress3;
	private String billingAddress4;
	private String zipCode;

}