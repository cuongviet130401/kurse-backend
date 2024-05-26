package edu.bitble.kurse.service;

import edu.bitble.kurse.common.JwtUtils;
import edu.bitble.kurse.common.SecurityHandler;
import edu.bitble.kurse.common.exception.AuthorizationException;
import edu.bitble.kurse.model.AccountRole;
import edu.bitble.kurse.model.OAuth2Provider;
import edu.bitble.kurse.model.dto.Account;
import edu.bitble.kurse.model.dto.User;
import edu.bitble.kurse.model.mapping.AccountMapper;
import edu.bitble.kurse.model.mapping.UserMapper;
import edu.bitble.kurse.model.persistence.AccountEntity;
import edu.bitble.kurse.model.persistence.UserEntity;
import edu.bitble.kurse.model.request.AuthenticationRequest;
import edu.bitble.kurse.model.request.CreateAccountRequest;
import edu.bitble.kurse.repository.AccountRepository;
import edu.bitble.kurse.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final MailService mailService;
    private final SimpleInternalCredentialService simpleInternalCredentialService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    //    public List<Account> getAllAccounts() {
//        return accountRepository.findAll();
//    }
//
    public Optional<Account> getAccountById(Integer id) {
        return accountRepository.findById(id)
                .map(AccountMapper.INSTANCE::toDto);
    }

    //
    public Optional<Account> searchAccountByIdentity(String q) {
        return accountRepository.findByUsernameIgnoreCase(q)
                .or(
                        () -> userRepository.findByEmail(q).flatMap(
                                (matchedUser) -> accountRepository.findById(matchedUser.getId())
                        )
                )
                .or(
                        () -> userRepository.findByPhoneNumber(q).flatMap(
                                (matchedUser) -> accountRepository.findById(matchedUser.getId())
                        )
                )
                .map(AccountMapper.INSTANCE::toDto);
    }

    //
////    public List<Account> searchAccountsByDisplayName(String displayName) {
////        return accountRepository.findAllByDisplayNameContainingIgnoreCase(displayName);
////    }
//
    @Transactional(rollbackOn = Exception.class)
    public Boolean createNewAccount(CreateAccountRequest reqEntity) {
        // check if there is already an existing account in database
        // require that the account need to register with email firstly via SELF_PROVIDED method

        searchAccountByIdentity(reqEntity.getEmail()).ifPresent(
                (e) -> {
                    throw new IllegalArgumentException("email:Account with given email is already exists");
                }
        );
        searchAccountByIdentity(reqEntity.getPhoneNumber()).ifPresent(
                (e) -> {
                    throw new IllegalArgumentException("phoneNumber:Account with given phone number is already exists");
                }
        );

        var persistedUser = userRepository.save(
                UserEntity.builder()
                        .firstName(reqEntity.getFirstName())
                        .lastName(reqEntity.getLastName())
                        .email(reqEntity.getEmail())
                        .phoneNumber(reqEntity.getPhoneNumber())
                        .isMale(true)
                        .build()
        );

        accountRepository.save(
                AccountEntity.builder()
                        .userId(persistedUser.getId())
                        .username(UUID.randomUUID().toString())
                        .createdAt(LocalDateTime.now())
                        .encryptedPassword(bCryptPasswordEncoder.encode(reqEntity.getPassword()))
                        .identifyProvider(OAuth2Provider.BITBLE)
                        .role(AccountRole.STUDENT_ACCOUNT)
                        .build()
        );

        return true;
    }

    //
////    public Account linkAccountWithAssociatedProvider(OAuth2AuthenticationRequestEntity reqEntity) {
////
////        Account loadedAccount = requireNonNull(
////                this.searchAccountByIdentity(reqEntity.getOauth2ProviderAccountIdentity()),
////                LOG_OAUTH2_INVALID_USER_IDENTITY
////        );
////
////        Account.AccountProvider oauth2Provider = Account.AccountProvider.valueOf(reqEntity.getOauth2ProviderProviderName().toUpperCase());
////        String associatedAccountId = loadedAccount.getAssociatedProviders().getOrDefault(oauth2Provider, null);
////        if (Objects.nonNull(associatedAccountId)) {
////            throw new IllegalArgumentException("Already linked account, invalid request. Please consider to unlink associated provider before renewing it.");
////        }
////
////        loadedAccount.getAssociatedProviders().put(oauth2Provider, reqEntity.getOauth2ProviderAccountId());
////
////        return accountRepository.save(loadedAccount);
////    }
//
////    public Account unlinkAccountWithAssociatedProvider(String accountId, String associatedProviderName) {
////        Account loadedAccount = requireNonNull(this.getAccountById(accountId), LOG_OAUTH2_INVALID_USER_IDENTITY);
////
////        Account.AccountProvider oauth2Provider = Account.AccountProvider.valueOf(associatedProviderName);
////        requireNonNull(
////                loadedAccount.getAssociatedProviders().getOrDefault(oauth2Provider, null),
////                LOG_OAUTH2_INVALID_USER_IDENTITY
////        );
////
////        loadedAccount.getAssociatedProviders().remove(oauth2Provider);
////        return accountRepository.save(loadedAccount);
////    }
//
////    public Map<String, Object> loginViaOAuth2Provider(OAuth2AuthenticationRequestEntity reqEntity) {
////        var loadedAccount = requireNonNull(
////                this.searchAccountByIdentity(reqEntity.getOauth2ProviderAccountIdentity()),
////                LOG_OAUTH2_INVALID_USER_IDENTITY
////        );
////
////        var oauth2Provider = Account.AccountProvider.valueOf(reqEntity.getOauth2ProviderProviderName().toUpperCase());
////        var associatedAccountId = loadedAccount.getAssociatedProviders().getOrDefault(oauth2Provider, null);
////
////        if (Objects.isNull(associatedAccountId) || !associatedAccountId.equals(reqEntity.getOauth2ProviderAccountId())) {
////            throw new NullPointerException("Invalid persisted associated accountId. This could be caused since the account is not linked with any OAuth2 provider.");
////        }
////
////        return Map.of(
////                "accessToken", JwtUtils.issueAuthenticatedAccessToken(loadedAccount),
////                "accountDocumentEntity", loadedAccount
////        );
////    }
//
    public Map<String, Object> login(AuthenticationRequest reqEntity) {
        Account loadedAccount = this.searchAccountByIdentity(reqEntity.getLoginIdentity()).orElseThrow(
                () -> new AuthorizationException("Invalid login identities")
        );

        if (!SecurityHandler.isMatchedPassword(reqEntity.getPassword(), loadedAccount.getEncryptedPassword())) {
            throw new AuthorizationException("Invalid login identities");
        }

        var loadedEntity = accountRepository.findById(loadedAccount.getId()).orElseThrow();
        loadedEntity.setLastSignInAt(LocalDateTime.now());
        accountRepository.save(loadedEntity);

        return Map.of(
                "accessToken", JwtUtils.issueAuthenticatedAccessToken(loadedAccount),
                "account", loadedAccount
        );
    }
//
//    public Object removeAccountById(String id) {
//        return accountRepository.findById(UUID.fromString(id))
//                .map(loadedEntity -> {
//                    accountRepository.delete(loadedEntity);
//                    return loadedEntity;
//                }).orElseThrow();
//    }
//
//    public Account updateExistingAccount(String identity, AccountRequestEntity reqEntity) {
//        var loadedAccount = requireNonNull(this.searchAccountByIdentity(identity));
//        AccountMapper.INSTANCE.updateExistingAccount(loadedAccount, reqEntity);
//
//        return accountRepository.save(loadedAccount);
//    }
//
//    @SneakyThrows
//    public Account updateFieldValueOfExistingAccount(String identity, String fieldName, Object newValue) {
//
//        final List<String> unmodifiableFields = List.of("accountId", "accountRole", "accountProvider", "hashedPassword");
//        if (unmodifiableFields.contains(fieldName)) {
//            throw new IllegalAccessException("Invalid action, unable to update unmodifiable field");
//        }
//
//        Account preparedAccount = requireNonNull(this.searchAccountByIdentity(identity));
//
//        Field preparedField = preparedAccount.getClass().getDeclaredField(fieldName);
//        preparedField.setAccessible(true);
//
//        log.info("Performs updating field value: "
//                + identity + "::" + "Account$"
//                + preparedField.getName() + "::" + preparedField.getType().getSimpleName()
//                + " : " + preparedField.get(preparedAccount)
//                + " => " + newValue
//        );
//
//        preparedField.set(preparedAccount, newValue);
//
//        return accountRepository.save(preparedAccount);
//    }
//
    @SneakyThrows
    public String issueResetPasswordMail(String emailAddress) {
        User matchedUser = userRepository.findByEmail(emailAddress)
                .map(UserMapper.INSTANCE::toDto)
                .orElseThrow(() -> new NullPointerException("There is no user associated with given email"));

        Account matchedAccount = accountRepository.findById(matchedUser.getId())
                .map(AccountMapper.INSTANCE::toDto)
                .orElseThrow();

        SimpleInternalCredentialService.ResetRequestCredential credentialEntry =
                this.simpleInternalCredentialService.issueAndPersistResetCredential(matchedAccount.getId());

        mailService.issueResetPassword(matchedUser, credentialEntry.getResetToken());

        return credentialEntry.getResetCredential();
    }

    public Boolean resetNewPasswordForExistingAccount(String resetCredential, String newHashedPassword) {
        var accountId = simpleInternalCredentialService.acceptResetCredential(resetCredential);
        var preparedAccount = accountRepository.findById(accountId).orElseThrow();

        preparedAccount.setEncryptedPassword(newHashedPassword);

        accountRepository.save(preparedAccount);

        return true;
    }

    public boolean validateResetToken(String resetCredential, String resetToken) {
        return simpleInternalCredentialService.isValid(resetCredential, resetToken);
    }

}
