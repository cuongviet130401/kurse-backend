package edu.bitble.kurse.model.dto;

import edu.bitble.kurse.model.AccountRole;
import edu.bitble.kurse.model.OAuth2Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer id;
    private Integer userId;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime lastSignInAt;
    private OAuth2Provider identifyProvider;
    private AccountRole role;
}
