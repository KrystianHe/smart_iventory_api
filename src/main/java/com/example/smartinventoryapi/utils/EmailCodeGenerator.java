package com.example.smartinventoryapi.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EmailCodeGenerator {
    private static final int CODE_LENGTH = 6;

    private static final long CODE_EXPIRATION_MINUTES = 5;

    public static class VerificationCode {
        private String code;
        private LocalDateTime expirationTime;

        public VerificationCode(String code, LocalDateTime expirationTime) {
            this.code = code;
            this.expirationTime = expirationTime;
        }

        public String getCode() {
            return code;
        }

        public LocalDateTime getExpirationTime() {
            return expirationTime;
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expirationTime);
        }
    }

    public static VerificationCode generateCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);

        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES);
        return new VerificationCode(String.valueOf(code), expirationTime);
    }

    public static void main(String[] args) {
        VerificationCode verificationCode = generateCode();
        System.out.println("Wygenerowany kod: " + verificationCode.getCode());
        System.out.println("Wygasa: " + verificationCode.getExpirationTime());
    }
}

