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
@Entity(name = "orders")
public class Order {

    @Id
    private UUID id;

    private OrderType orderType;

    private UUID cartId;

    private UUID transactionId;

    private LocalDateTime createdDateTime;

    private LocalDateTime paymentDateTime;

    private Double total;

    enum OrderType {
        PURCHASE,
        DISCOUNT,
        REFUND,
        OTHER
    }

}
