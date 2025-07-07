package ru.kaznacheev.wallet.operationservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "ru.kaznacheev.wallet.operationservice",
        "ru.kaznacheev.wallet.common"
})
public class PackageScanConfig {
}
