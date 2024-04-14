package edu.bitble.kurse.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;

    private String firstName;
    private String lastName;

    private Integer defaultPaymentMethodId;
    private Boolean isMale;
    private String avatarUrl;

    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String country;
}
