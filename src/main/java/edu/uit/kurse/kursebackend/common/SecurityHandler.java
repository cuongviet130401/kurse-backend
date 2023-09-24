package edu.uit.kurse.kursebackend.common;

import edu.rmit.highlandmimic.model.User;
import edu.rmit.highlandmimic.repository.UserRepository;
import edu.uit.kurse.kursebackend.model.AccountRole;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static edu.rmit.highlandmimic.common.ControllerUtils.controllerWrapper;
import static edu.rmit.highlandmimic.common.ExceptionLogger.logInvalidAction;
import static edu.uit.kurse.kursebackend.common.ControllerUtils.controllerWrapper;
import static edu.uit.kurse.kursebackend.common.ExceptionLogger.logInvalidAction;

@Component
@RequiredArgsConstructor
public class SecurityHandler {

    public static final List<AccountRole> ALLOW_AUTHORITIES = List.of(AccountRole.ADMIN, AccountRole.LECTURER);
    public static final List<AccountRole> ALLOW_STAKEHOLDERS = List.of(AccountRole.ADMIN, AccountRole.LECTURER, AccountRole.STUDENT);

    private final UserRepository userRepository;

    public ResponseEntity<?> roleGuarantee(@NotBlank String authenticationToken,
                                           @NotEmpty List<AccountRole> allowedRoles,
                                           @NotNull Supplier<?> serviceExecutionSupplier) {
        User.UserRole requestUserRole;
        try {
            if (Strings.isBlank(authenticationToken)) {
                throw new NullPointerException("Action requires providing of the authentication token");
            }

            if (Objects.isNull(claims)) {
                throw new NullPointerException();
            }

            if (claims.getExpiration().getTime() < System.currentTimeMillis()) {
                throw new IllegalStateException("Expired jwt token");
            }

            String[] tokens = claims.getSubject().split("~");
            requestUserRole = User.UserRole.valueOf(tokens[1]);

            DefaultClaims claims = JwtUtils.decodeJwtToken(authenticationToken.split(" ")[1]);


            if(!checkIfUserIdIsExist(tokens[0]) || !checkIfRoleMatchesWithUser(tokens[0], User.UserRole.valueOf(tokens[1]))) {
                throw new IllegalArgumentException("Invalid user information");
            }

            if (!allowedRoles.contains(requestUserRole)) {
                throw new IllegalAccessException("User has insufficient privileges to perform this action");
            }
        } catch (Exception e) {
            logInvalidAction(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }

        return controllerWrapper(serviceExecutionSupplier);
    }

    public ResponseEntity<?> roleGuarantee(String authenticationToken,
                                           AccountRole allowedRole,
                                           Supplier<?> serviceExecutionSupplier) {
        return this.roleGuarantee(authenticationToken, List.of(allowedRole), serviceExecutionSupplier);
    }

    private boolean checkIfUserIdIsExist(String userId) {
        return userRepository.existsByUserId(userId);
    }

    private boolean checkIfRoleMatchesWithUser(String userId, User.UserRole role) {
        return userRepository.findById(userId)
                .map(user -> user.getUserRole().equals(role))
                .orElseThrow(() -> new NullPointerException("Invalid user information"));
    }

}
