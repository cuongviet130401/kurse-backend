package edu.uit.kurse.kursebackend.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAccountRequestEntity {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "", message = ValidationErrorMessages.PHONE_NUMBER_PATTERN)
    private String phoneNumber;

    @NotBlank
    @Size(min = 8, message = ValidationErrorMessages.PASSWORD_SIZE)
    private String password;

}
