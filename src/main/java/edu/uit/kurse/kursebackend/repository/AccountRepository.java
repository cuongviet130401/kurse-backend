package edu.uit.kurse.kursebackend.repository;

import edu.uit.kurse.kursebackend.model.AccountRole;
import edu.uit.kurse.kursebackend.model.persistence.AccountPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountPersistenceEntity, Integer> {

    Optional<AccountPersistenceEntity> findByUsernameIgnoreCase(String username);
//    Optional<AccountPersistenceEntity> findByEmailIgnoreCase(String email);
//    Optional<AccountPersistenceEntity> findByPhoneNumber(String phoneNumber);
    Optional<AccountRole> getAccountRoleById(Integer id);

}
