package ru.kaznacheev.wallet.operation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для сканирования пакетов.
 */
@Configuration
@ComponentScan(basePackages = {
        "ru.kaznacheev.wallet.operation",
        "ru.kaznacheev.wallet.common"
})
public class PackageScanConfig {
}
