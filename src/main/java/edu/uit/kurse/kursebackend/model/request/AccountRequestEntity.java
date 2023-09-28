package edu.uit.kurse.kursebackend.model.request;

import edu.uit.kurse.kursebackend.model.AccountRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequestEntity {

    @NotBlank
    @Min(6)
    private String username;

    @NotBlank
    @Min(8)
    private String password;

    @Email
    private String email;

    @NotBlank
    @Max(10)
    @Min(10)
    private String phoneNumber;

    @Builder.Default
    private AccountRole role = AccountRole.STUDENT;

}
