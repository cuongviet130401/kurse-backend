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
@Table(name = "USER")
@NoArgsConstructor
@AllArgsConstructor
public class UserPersistenceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private Integer paymentMethodId;

	@NotNull
	private String firstName;

	@NotNull
	private String middleName;

	@NotNull
	private String lastName;

	@NotNull
	private String isMale;

	@NotNull
	private String avatarUrl;

	@NotNull
	private String address1;

	@NotNull
	private String address2;

	@NotNull
	private String address3;

	@NotNull
	private String address4;

}