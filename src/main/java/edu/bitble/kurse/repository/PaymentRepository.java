package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    List<PaymentEntity> findAllByNameContainsIgnoreCase(String name);

}