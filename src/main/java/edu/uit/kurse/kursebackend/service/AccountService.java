package edu.uit.kurse.kursebackend.service;

import edu.uit.kurse.kursebackend.common.JwtUtils;
import edu.uit.kurse.kursebackend.common.SecurityHandler;
import edu.uit.kurse.kursebackend.model.AccountRole;
import edu.uit.kurse.kursebackend.model.mapping.AccountMapper;
import edu.uit.kurse.kursebackend.model.persistent.Account;
import edu.uit.kurse.kursebackend.model.request.AccountRequestEntity;
import edu.uit.kurse.kursebackend.model.request.AuthenticationRequestEntity;
import edu.uit.kurse.kursebackend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final MailService mailService;
    private final SimpleInternalCredentialService simpleInternalCredentialService;


    /* Test only */
    public long removeAllAccounts() {
        long quantity = accountRepository.count();
        accountRepository.deleteAll();
        return quantity;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(String id) {
        return accountRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public Account searchAccountByIdentity(String q) {
        return accountRepository.findByEmailIgnoreCase(q)
                .or(() -> accountRepository.findByUsernameIgnoreCase(q))
                .or(() -> accountRepository.findByPhoneNumber(q))
                .orElse(null);
    }

//    public List<Account> searchAccountsByDisplayName(String displayName) {
//        return accountRepository.findAllByDisplayNameContainingIgnoreCase(displayName);
//    }

    @Transactional(noRollbackFor = Exception.class)
    public Object createNewAccount(AccountRequestEntity reqEntity) {
        // check if there is already an existing account in database
        // require that the account need to register with email firstly via SELF_PROVIDED method

        if (Objects.nonNull(this.searchAccountByIdentity(reqEntity.getEmail()))) {
            throw new IllegalArgumentException(String.format("Account with given email '%s' is already exists", reqEntity.getEmail()));
        }
        if (Objects.nonNull(this.searchAccountByIdentity(reqEntity.getPhoneNumber()))) {
            throw new IllegalArgumentException(String.format("Account with given phone number '%s' is already exists", reqEntity.getPhoneNumber()));
        }
        if (Objects.nonNull(this.searchAccountByIdentity(reqEntity.getUsername()))) {
            throw new IllegalArgumentException(String.format("Account with given username '%s' is already exists", reqEntity.getUsername()));
        }

        var preparedAccount = AccountMapper.INSTANCE.toPersistent(reqEntity);

        return accountRepository.save(preparedAccount);
    }

//    public Account linkAccountWithAssociatedProvider(OAuth2AuthenticationRequestEntity reqEntity) {
//
//        Account loadedAccount = requireNonNull(
//                this.searchAccountByIdentity(reqEntity.getOauth2ProviderAccountIdentity()),
//                LOG_OAUTH2_INVALID_USER_IDENTITY
//        );
//
//        Account.AccountProvider oauth2Provider = Account.AccountProvider.valueOf(reqEntity.getOauth2ProviderProviderName().toUpperCase());
//        String associatedAccountId = loadedAccount.getAssociatedProviders().getOrDefault(oauth2Provider, null);
//        if (Objects.nonNull(associatedAccountId)) {
//            throw new IllegalArgumentException("Already linked account, invalid request. Please consider to unlink associated provider before renewing it.");
//        }
//
//        loadedAccount.getAssociatedProviders().put(oauth2Provider, reqEntity.getOauth2ProviderAccountId());
//
//        return accountRepository.save(loadedAccount);
//    }

//    public Account unlinkAccountWithAssociatedProvider(String accountId, String associatedProviderName) {
//        Account loadedAccount = requireNonNull(this.getAccountById(accountId), LOG_OAUTH2_INVALID_USER_IDENTITY);
//
//        Account.AccountProvider oauth2Provider = Account.AccountProvider.valueOf(associatedProviderName);
//        requireNonNull(
//                loadedAccount.getAssociatedProviders().getOrDefault(oauth2Provider, null),
//                LOG_OAUTH2_INVALID_USER_IDENTITY
//        );
//
//        loadedAccount.getAssociatedProviders().remove(oauth2Provider);
//        return accountRepository.save(loadedAccount);
//    }

//    public Map<String, Object> loginViaOAuth2Provider(OAuth2AuthenticationRequestEntity reqEntity) {
//        var loadedAccount = requireNonNull(
//                this.searchAccountByIdentity(reqEntity.getOauth2ProviderAccountIdentity()),
//                LOG_OAUTH2_INVALID_USER_IDENTITY
//        );
//
//        var oauth2Provider = Account.AccountProvider.valueOf(reqEntity.getOauth2ProviderProviderName().toUpperCase());
//        var associatedAccountId = loadedAccount.getAssociatedProviders().getOrDefault(oauth2Provider, null);
//
//        if (Objects.isNull(associatedAccountId) || !associatedAccountId.equals(reqEntity.getOauth2ProviderAccountId())) {
//            throw new NullPointerException("Invalid persisted associated accountId. This could be caused since the account is not linked with any OAuth2 provider.");
//        }
//
//        return Map.of(
//                "accessToken", JwtUtils.issueAuthenticatedAccessToken(loadedAccount),
//                "accountDocumentEntity", loadedAccount
//        );
//    }

    public Map<String, Object> login(AuthenticationRequestEntity reqEntity) {
        Account loadedAccount = requireNonNull(this.searchAccountByIdentity(reqEntity.getLoginIdentity()));

        if (!SecurityHandler.isMatchedPassword(reqEntity.getPassword(), loadedAccount.getPassword())) {
            throw new NullPointerException("Invalid login identities");
        }

        return Map.of(
                "accessToken", JwtUtils.issueAuthenticatedAccessToken(loadedAccount),
                "accountDocumentEntity", loadedAccount
        );
    }

    public Object removeAccountById(String id) {
        return accountRepository.findById(UUID.fromString(id))
                .map(loadedEntity -> {
                    accountRepository.delete(loadedEntity);
                    return loadedEntity;
                }).orElseThrow();
    }

    public Account updateExistingAccount(String identity, AccountRequestEntity reqEntity) {
        var loadedAccount = requireNonNull(this.searchAccountByIdentity(identity));
        AccountMapper.INSTANCE.updateExistingAccount(loadedAccount, reqEntity);

        return accountRepository.save(loadedAccount);
    }

    @SneakyThrows
    public Account updateFieldValueOfExistingAccount(String identity, String fieldName, Object newValue) {

        final List<String> unmodifiableFields = List.of("accountId", "accountRole", "accountProvider", "hashedPassword");
        if (unmodifiableFields.contains(fieldName)) {
            throw new IllegalAccessException("Invalid action, unable to update unmodifiable field");
        }

        Account preparedAccount = requireNonNull(this.searchAccountByIdentity(identity));

        Field preparedField = preparedAccount.getClass().getDeclaredField(fieldName);
        preparedField.setAccessible(true);

        log.info("Performs updating field value: "
                + identity + "::" + "Account$"
                + preparedField.getName() + "::" + preparedField.getType().getSimpleName()
                + " : " + preparedField.get(preparedAccount)
                + " => " + newValue
        );

        preparedField.set(preparedAccount, newValue);

        return accountRepository.save(preparedAccount);
    }

    @SneakyThrows
    public String issueResetPasswordMail(String emailAddress) {
        Account receiver = accountRepository.findByEmailIgnoreCase(emailAddress)
                .orElseThrow(() -> new NullPointerException("Invalid email address: " + emailAddress));

        SimpleInternalCredentialService.ResetRequestCredential credentialEntry =
                this.simpleInternalCredentialService.issueAndPersistResetCredential(receiver.getUsername());

        mailService.issueResetPassword(receiver, credentialEntry.getResetToken());

        return credentialEntry.getResetCredential();
    }

    public Account resetNewPasswordForExistingAccount(String resetCredential, String newHashedPassword) {
        String accountEmail = simpleInternalCredentialService.acceptResetCredential(resetCredential);
        Account preparedAccount = this.searchAccountByIdentity(accountEmail);

        preparedAccount.setPassword(newHashedPassword);

        return accountRepository.save(preparedAccount);
    }

    public Account changeRoleOfExistingAccount(String identity, String newRole) {
        Account preparedAccount = requireNonNull(this.searchAccountByIdentity(identity));

        preparedAccount.setRole(AccountRole.valueOf(newRole));

        return accountRepository.save(preparedAccount);
    }

    public boolean validateResetToken(String resetCredential, String resetToken) {
        return simpleInternalCredentialService.isValid(resetCredential, resetToken);
    }
    
}
