package edu.uit.kurse.kursebackend.model.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uit.kurse.kursebackend.model.AccountRole;
import edu.uit.kurse.kursebackend.model.OAuth2Provider;
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