package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.CourseRatingRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRatingRecordRepository extends JpaRepository<CourseRatingRecordEntity, Integer> {

}