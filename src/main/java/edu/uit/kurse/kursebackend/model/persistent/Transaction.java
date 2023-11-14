package edu.uit.kurse.kursebackend.model.persistent;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
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
@Entity(name = "KURSE_TRANSACTIONS")
@Table(name = "KURSE_TRANSACTIONS")
public class Transaction {

    @Id
    private UUID id;

    private UUID senderId;

    private UUID receivedId;

    private LocalDateTime createdDateTime;

    @Column(name = "transval")
    private Double value;

    private String description;

    private String thirdPartyTransactionId;

}
