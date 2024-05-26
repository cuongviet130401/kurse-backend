package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.CourseLikeRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLikeRecordRepository extends JpaRepository<CourseLikeRecordEntity, Integer> {

}