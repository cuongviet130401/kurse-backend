package edu.uit.kurse.kursebackend.model.request;

import edu.uit.kurse.kursebackend.model.AccountRole;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequestEntity {

    @NotBlank
    @Size(min = 6)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @NotNull
    private AccountRole role;

}
