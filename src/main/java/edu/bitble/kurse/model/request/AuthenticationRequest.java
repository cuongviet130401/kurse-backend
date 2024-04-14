package edu.bitble.kurse.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

    @NotBlank
    private String loginIdentity;

    @NotBlank
    private String password;
}
