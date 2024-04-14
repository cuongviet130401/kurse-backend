package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.CourseLikeRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseLikeRecordRepository extends JpaRepository<CourseLikeRecordEntity, Integer> {

    List<CourseLikeRecordEntity> findAllByNameContainsIgnoreCase(String name);

}