package ru.kaznacheev.wallet.gateway.dto;

public class VerifyResponse {

    private final String userId;

    public VerifyResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
