package edu.bitble.kurse.common;

import edu.bitble.kurse.model.AccountRole;
import edu.bitble.kurse.repository.AccountRepository;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

import static edu.bitble.kurse.common.ControllerUtils.controllerWrapper;
import static edu.bitble.kurse.common.ExceptionLogger.logInvalidAction;
import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class SecurityHandler {

    public static final List<AccountRole> ALLOW_AUTHORITIES = List.of(AccountRole.MANAGER_ACCOUNT, AccountRole.TEACHER_ACCOUNT);
    public static final List<AccountRole> ALLOW_ACTORS = List.of(AccountRole.MANAGER_ACCOUNT, AccountRole.TEACHER_ACCOUNT, AccountRole.STUDENT_ACCOUNT);

    private final AccountRepository accountRepository;

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);

    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean isMatchedPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    @Valid
    public ResponseEntity<?> roleGuarantee(@NotBlank String authenticationToken,
                                           @NotEmpty List<AccountRole> allowedRoles,
                                           @NotNull Supplier<?> serviceExecutionSupplier) {
        AccountRole requestAccountRole;
        try {
            if (Strings.isBlank(authenticationToken)) {
                throw new NullPointerException("Action requires providing of the authentication token");
            }

            DefaultClaims claims = JwtUtils.decodeJwtToken(authenticationToken.split(" ")[1]);
            requireNonNull(claims);

            if (claims.getExpiration().getTime() < System.currentTimeMillis()) {
                throw new IllegalStateException("Expired jwt token");
            }

            String[] tokens = claims.getSubject().split("~");
            var accountId = Integer.parseInt(tokens[0]);
            requestAccountRole = AccountRole.valueOf(tokens[1]);

            if(!checkIfAccountIdIsExist(accountId) || !checkIfRoleMatchesWithAccount(accountId, AccountRole.valueOf(tokens[1]))) {
                throw new IllegalArgumentException("Invalid account information");
            }

            if (!allowedRoles.contains(requestAccountRole)) {
                throw new IllegalAccessException("Account has insufficient privileges to perform this action");
            }
        } catch (Exception e) {
            logInvalidAction(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        return ControllerUtils.controllerWrapper(serviceExecutionSupplier);
    }

    public ResponseEntity<?> roleGuarantee(String authenticationToken,
                                           AccountRole allowedRole,
                                           Supplier<?> serviceExecutionSupplier) {
        return this.roleGuarantee(authenticationToken, List.of(allowedRole), serviceExecutionSupplier);
    }

    private boolean checkIfAccountIdIsExist(Integer accountId) {
        return accountRepository.existsById(accountId);
    }

    private boolean checkIfRoleMatchesWithAccount(Integer accountId, AccountRole role) {
        return accountRepository.getAccountRoleById(accountId)
                .map(role::equals)
                .orElseThrow(() -> new NullPointerException("Invalid account information"));
    }

}
