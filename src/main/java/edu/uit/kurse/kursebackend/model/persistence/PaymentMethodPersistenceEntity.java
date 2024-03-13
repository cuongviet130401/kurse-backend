package edu.uit.kurse.kursebackend.model.persistence;

import edu.uit.kurse.kursebackend.model.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "PAYMENT_METHOD")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodPersistenceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private Integer userId;

	@NotNull
	private PaymentType paymentType;

	@NotNull
	private String bankCode;

	@NotNull
	private String bankAccount;

	@NotNull
	private String cardNumber;

	@NotNull
	private String cardHolderName;

	@NotNull
	private String expiredDate;

	@NotNull
	private String billingAddress1;

	@NotNull
	private String billingAddress2;

	@NotNull
	private String billingAddress3;

	@NotNull
	private String billingAddress4;

	@NotNull
	private String zipCode;

}