package edu.uit.kurse.kursebackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private Integer paymentMethodId;
    private String firstName;
    private String lastName;
    private String isMale;
    private String avatarUrl;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String email;
    private String phoneNumber;
}
