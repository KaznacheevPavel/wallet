package ru.kaznacheev.wallet.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Конфигурация для времени и даты.
 */
@Configuration
public class ClockConfig {

    /**
     * Возвращает {@code Bean} {@link Clock}.
     *
     * @return {@link Clock}, возвращающий время в UTC
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}
