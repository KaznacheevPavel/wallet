package ru.kaznacheev.wallet.authservice.service;

public interface RefreshWhitelistService {

    void saveToken(String accessToken, String refreshToken);

    String revokeToken(String refreshToken);

}
