package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.persistence.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    List<StudentEntity> findAllByNameContainsIgnoreCase(String name);

}
