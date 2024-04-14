package edu.bitble.kurse.model.persistence;

import edu.bitble.kurse.model.OAuth2Provider;
import edu.bitble.kurse.model.AccountRole;
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
@Table(name = "BITBLE_ACCOUNT")
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private Integer userId;

	@NotNull
	private String username;

	@NotNull
	private String encryptedPassword;

	@CreatedDate
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastSignInAt;

	@Enumerated(EnumType.STRING)
	@NotNull
	private OAuth2Provider identifyProvider;

	@Enumerated(EnumType.STRING)
	@NotNull
	private AccountRole role;

}