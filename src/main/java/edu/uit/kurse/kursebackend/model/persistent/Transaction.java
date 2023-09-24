package edu.uit.kurse.kursebackend.model.persistent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transactions")
public class Transaction {

    @Id
    private UUID id;

    private UUID senderId;

    private UUID receivedId;

    private LocalDateTime createdDateTime;

    private Double value;

    private String description;

    private String thirdPartyTransactionId;

}
