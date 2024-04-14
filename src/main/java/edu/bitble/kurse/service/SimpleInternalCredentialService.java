package edu.bitble.kurse.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleInternalCredentialService {

    private final List<ResetRequestCredential> resetRequestCredentials;

    public ResetRequestCredential issueAndPersistResetCredential(Integer accountId) {
        var uuid = UUID.randomUUID().toString();
        var tokenSource = UUID.randomUUID().toString();
        var resetToken = tokenSource.substring(tokenSource.length() - 6);

        var preparedCredential = new ResetRequestCredential(uuid, resetToken, accountId);
        resetRequestCredentials.add(preparedCredential);

        return preparedCredential;
    }

    public Integer acceptResetCredential(String credential) {
        return resetRequestCredentials.stream()
                .filter(e -> e.getResetCredential().equalsIgnoreCase(credential))
                .findFirst()
                .map(e -> {
                    var issuedAccountId = e.getIssuedAccountId();
                    resetRequestCredentials.remove(e);
                    return issuedAccountId;
                }).orElseThrow();
    }

    public boolean isValid(String resetCredential, String resetToken) {

        return resetRequestCredentials.stream()
                .filter(e -> e.getResetCredential().equalsIgnoreCase(resetCredential))
                .findFirst()
                .map(e -> {
                    boolean result = e.getResetToken().equalsIgnoreCase(resetToken);
                    log.info(
                            "[VALIDATE@" + resetCredential + "] resetToken: "
                                    + resetToken
                                    + "; is matched with stored: " + e.getResetToken() + " => " + result
                    );
                    return result;
                }).orElseThrow();
    }

    @AllArgsConstructor
    @Getter
    public static class ResetRequestCredential {
        private String resetCredential;
        private String resetToken;
        private Integer issuedAccountId;
    }

}

