package ru.kaznacheev.wallet.authservice.service;

public interface AccessBlacklistService {

    void addToBlacklist(String jti);

    void verify(String jti);

}
