package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.EnrolmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolmentRepository extends JpaRepository<EnrolmentEntity, Integer> {

}