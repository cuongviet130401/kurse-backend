package edu.bitble.kurse.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Integer defaultPaymentMethodId;

    private Boolean isMale;

    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String country;

    private String avatarUrl;
}
