package ru.kaznacheev.wallet.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.kaznacheev.wallet.common.config.YamlPropertySourceFactory;

@Configuration("authPropertySourceConfig")
@PropertySource(value = "classpath:constraint/auth-constraints.yaml", factory = YamlPropertySourceFactory.class)
public class PropertySourceConfig {
}
