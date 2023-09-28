package edu.uit.kurse.kursebackend.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequestEntity {

    @NotBlank
    private String loginIdentity;

    @NotBlank
    private String password;
}
