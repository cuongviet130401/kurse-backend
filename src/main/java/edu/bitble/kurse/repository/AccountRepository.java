package edu.bitble.kurse.repository;

import edu.bitble.kurse.model.AccountRole;
import edu.bitble.kurse.model.persistence.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    Optional<AccountEntity> findByUsernameIgnoreCase(String username);
//    Optional<AccountPersistenceEntity> findByEmailIgnoreCase(String email);
//    Optional<AccountPersistenceEntity> findByPhoneNumber(String phoneNumber);
    Optional<AccountRole> getAccountRoleById(Integer id);

}
