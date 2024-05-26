package edu.bitble.kurse.model.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "STUDENT")
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Integer accountId;

    private Integer paymentMethodId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Boolean isMale;

    private String avatarUrl;

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;
}