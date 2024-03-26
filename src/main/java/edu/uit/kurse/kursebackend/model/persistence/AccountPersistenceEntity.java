package edu.uit.kurse.kursebackend.model.persistence;

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
@Table(name = "BITBLE_ACCOUNT")
@NoArgsConstructor
@AllArgsConstructor
public class AccountPersistenceEntity {

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
	@Column(name = "createdAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime lastSignInAt;

	@Enumerated(EnumType.STRING)
	@NotNull
	private OAuth2Provider identifyProvider;

	@NotNull
	private AccountRole role;

}