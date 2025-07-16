package ru.kaznacheev.wallet.operationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.kaznacheev.wallet.operationservice.service.MessageSourceService;

@Service
@RequiredArgsConstructor
public class MessageSourceServiceImpl implements MessageSourceService {

    @Qualifier("operationMessageSource")
    private final MessageSource messageSource;

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
