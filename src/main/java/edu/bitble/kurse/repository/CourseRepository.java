package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

    List<CourseEntity> findAllByNameContainsIgnoreCase(String name);

}