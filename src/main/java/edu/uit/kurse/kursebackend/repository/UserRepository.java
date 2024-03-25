package edu.uit.kurse.kursebackend.repository;

import edu.uit.kurse.kursebackend.model.persistence.UserPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserPersistenceEntity, Integer> {

    Optional<UserPersistenceEntity> findByEmail(String email);

    Optional<UserPersistenceEntity> findByPhoneNumber(String phoneNumber);

}
