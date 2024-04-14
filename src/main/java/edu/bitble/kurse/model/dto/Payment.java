package edu.bitble.kurse.model.dto;

import edu.bitble.kurse.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Integer id;
    private Integer userId;
    private Integer enrolmentId;
    private Integer courseId;
    private Integer paymentMethodId;
    private LocalDateTime createdAt;
    private double coursePrice;
    private Integer appliedVoucherId;
    private double paymentAmount;
    private PaymentStatus status;

}