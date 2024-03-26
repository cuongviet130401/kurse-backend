package edu.uit.kurse.kursebackend.service;

import edu.uit.kurse.kursebackend.model.OAuth2Provider;
import edu.uit.kurse.kursebackend.model.dto.Account;
import edu.uit.kurse.kursebackend.model.dto.User;
import edu.uit.kurse.kursebackend.model.mapping.AccountMapper;
import edu.uit.kurse.kursebackend.model.mapping.UserMapper;
import edu.uit.kurse.kursebackend.model.persistence.AccountPersistenceEntity;
import edu.uit.kurse.kursebackend.model.persistence.UserPersistenceEntity;
import edu.uit.kurse.kursebackend.model.request.CreateAccountRequestEntity;
import edu.uit.kurse.kursebackend.repository.AccountRepository;
import edu.uit.kurse.kursebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
//    @Transactional
    public Boolean createNewAccount(CreateAccountRequestEntity reqEntity) {
        // check if there is already an existing account in database
        // require that the account need to register with email firstly via SELF_PROVIDED method

        searchAccountByIdentity(reqEntity.getEmail()).ifPresent(
                (e) -> {
                    throw new IllegalArgumentException("Account with given email is already exists");
                }
        );
        searchAccountByIdentity(reqEntity.getPhoneNumber()).ifPresent(
                (e) -> {
                    throw new IllegalArgumentException("Account with given phone number is already exists");
                }
        );

        var persistedUser = userRepository.save(
                UserPersistenceEntity.builder()
                        .firstName(reqEntity.getFirstName())
                        .lastName(reqEntity.getLastName())
                        .email(reqEntity.getEmail())
                        .phoneNumber(reqEntity.getPhoneNumber())
                        .isMale(true)
                        .build()
        );

        accountRepository.save(
                AccountPersistenceEntity.builder()
                        .userId(persistedUser.getId())
                        .username(UUID.randomUUID().toString())
                        .encryptedPassword(bCryptPasswordEncoder.encode(reqEntity.getPassword()))
                        .identifyProvider(OAuth2Provider.BITBLE)
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
//    public Map<String, Object> login(AuthenticationRequestEntity reqEntity) {
//        Account loadedAccount = requireNonNull(this.searchAccountByIdentity(reqEntity.getLoginIdentity()));
//
//        if (!SecurityHandler.isMatchedPassword(reqEntity.getPassword(), loadedAccount.getPassword())) {
//            throw new NullPointerException("Invalid login identities");
//        }
//
//        return Map.of(
//                "accessToken", JwtUtils.issueAuthenticatedAccessToken(loadedAccount),
//                "accountDocumentEntity", loadedAccount
//        );
//    }
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
