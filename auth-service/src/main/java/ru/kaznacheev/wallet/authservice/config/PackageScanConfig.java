package ru.kaznacheev.wallet.authservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "ru.kaznacheev.wallet.authservice",
        "ru.kaznacheev.wallet.common"
})
public class PackageScanConfig {
}
