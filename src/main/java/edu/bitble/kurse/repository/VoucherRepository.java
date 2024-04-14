package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Integer> {

    List<VoucherEntity> findAllByNameContainsIgnoreCase(String name);

}