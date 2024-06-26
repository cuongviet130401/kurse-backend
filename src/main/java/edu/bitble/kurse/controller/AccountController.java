package edu.bitble.kurse.controller;

import edu.bitble.kurse.common.ControllerUtils;
import edu.bitble.kurse.common.SecurityHandler;
import edu.bitble.kurse.model.request.AuthenticationRequest;
import edu.bitble.kurse.model.request.CreateAccountRequest;
import edu.bitble.kurse.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;
    private final SecurityHandler securityHandler;

    @GetMapping("/issue-rspwmail")
    public ResponseEntity<?> resetPassword(@RequestParam @NotBlank String email) {
        return ControllerUtils.controllerWrapper(() -> accountService.issueResetPasswordMail(email));
    }

    @PostMapping("/validate-reset-token")
    public ResponseEntity<?> validateResetToken(@RequestParam String resetCredential,
                                                @RequestBody String resetToken) {
        return ControllerUtils.controllerWrapper(() -> accountService.validateResetToken(resetCredential, resetToken));
    }
//
//    @GetMapping
//    public ResponseEntity<?> getAllAccounts(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken) {
//        return securityHandler.roleGuarantee(
//                authorizationToken, SecurityHandler.ALLOW_AUTHORITIES,
//                accountService::getAllAccounts
//        );
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Account> getAccountById(@PathVariable String id) {
//        return ResponseEntity.ok(accountService.getAccountById(id));
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<?> searchAccountsByIdentity(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken,
//                                                      @RequestParam String q) {
//        return securityHandler.roleGuarantee(
//                authorizationToken, SecurityHandler.ALLOW_AUTHORITIES,
//                () -> accountService.searchAccountByIdentity(q)
//        );
//    }
//
////    @GetMapping("/search/name")
////    public ResponseEntity<List<Account>> searchAccountsByDisplayName(@RequestParam String q) {
////        return ResponseEntity.ok(accountService.searchAccountsByDisplayName(q));
////    }
//
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest reqEntity) {
        return ResponseEntity.ok(accountService.login(reqEntity));
    }

////    @PostMapping("/oauth2/login")
////    public ResponseEntity<?> loginViaOAuth2Provider(@RequestBody OAuth2AuthenticationRequestEntity reqEntity) {
////        return controllerWrapper(() -> accountService.loginViaOAuth2Provider(reqEntity));
////    }
//
//    // WRITE operation
//
    @PostMapping("/signup")
    public ResponseEntity<Boolean> createNewAccount(@Valid @RequestBody CreateAccountRequest reqEntity) {
        return ResponseEntity.ok(accountService.createNewAccount(reqEntity));
    }
//
//    // MODIFY operation
//
//    @PostMapping("/{identity}")
//    public ResponseEntity<?> updateExistingAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken,
//                                                   @PathVariable String identity,
//                                                   @RequestBody AccountRequestEntity reqEntity) {
//        return securityHandler.roleGuarantee(
//                authorizationToken, SecurityHandler.ALLOW_STAKEHOLDERS,
//                () -> accountService.updateExistingAccount(identity, reqEntity)
//        );
//    }
//
//    @PostMapping("/{identity}/{fieldName}")
//    public ResponseEntity<?> updateFieldValueOfExistingAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken,
//                                                               @PathVariable String identity,
//                                                               @PathVariable String fieldName,
//                                                               @RequestBody Object newValue) {
//        return securityHandler.roleGuarantee(
//                authorizationToken, SecurityHandler.ALLOW_STAKEHOLDERS,
//                () -> accountService.updateFieldValueOfExistingAccount(identity, fieldName, newValue)
//        );
//    }
//
//    @PostMapping("/{identity}/role")
//    public ResponseEntity<?> changeRoleOfExistingAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken,
//                                                         @PathVariable String identity,
//                                                         @RequestBody String newRole) {
//        return securityHandler.roleGuarantee(
//                authorizationToken, AccountRole.ADMIN,
//                () -> accountService.changeRoleOfExistingAccount(identity, newRole)
//        );
//    }
//
    @PostMapping("/reset-pass")
    public ResponseEntity<?> resetNewPasswordForExistingAccount(@RequestParam String resetCredential,
                                                                @RequestBody String newHashedPassword) {
        return ControllerUtils.controllerWrapper(() -> accountService.resetNewPasswordForExistingAccount(resetCredential, newHashedPassword));
    }

//    @PostMapping("/oauth2/link")
//    public ResponseEntity<?> linkAccountWithAssociatedProvider(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken,
//                                                               @RequestBody OAuth2AuthenticationRequestEntity reqEntity) {
//        return securityHandler.roleGuarantee(
//                authorizationToken,
//                () -> accountService.linkAccountWithAssociatedProvider(reqEntity),
//                SecurityHandler.ALLOW_STAKEHOLDERS
//        );
//    }
//
////    @PostMapping("/oauth2/unlink")
////    public ResponseEntity<?> unlinkAccountWithAssociatedProvider(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken,
////                                                                 @RequestParam String userId,
////                                                                 @RequestParam String providerName) {
////        return securityHandler.roleGuarantee(
////                authorizationToken,
////                () -> accountService.unlinkAccountWithAssociatedProvider(userId, providerName),
////                SecurityHandler.ALLOW_STAKEHOLDERS
////        );
////    }
//
//    // DELETE operation
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> removeAccountById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken,
//                                               @PathVariable String id) {
//        return securityHandler.roleGuarantee(
//                authorizationToken, SecurityHandler.ALLOW_STAKEHOLDERS,
//                () -> accountService.removeAccountById(id)
//        );
//    }

}
