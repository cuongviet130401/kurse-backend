package edu.uit.kurse.kursebackend.model.dao;

import edu.uit.kurse.kursebackend.model.AccountRole;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Account {

    @Id
    private UUID id;

    private AccountRole role;

    private String phoneNumber;

    private String email;

    private String username;

    private String password;

}
