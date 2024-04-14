package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Integer> {

    List<PaymentMethodEntity> findAllByNameContainsIgnoreCase(String name);

}