package ru.kaznacheev.wallet.operationservice.service;

public interface MessageSourceService {

    String getMessage(String code);

    String getMessage(String code, Object[] args);

}
