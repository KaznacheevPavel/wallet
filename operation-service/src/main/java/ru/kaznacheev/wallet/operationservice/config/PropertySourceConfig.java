package ru.kaznacheev.wallet.operationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.kaznacheev.wallet.common.config.YamlPropertySourceFactory;

@Configuration("operationPropertySourceConfig")
@PropertySource(value = "classpath:constraint/operation-constraints.yaml", factory = YamlPropertySourceFactory.class)
public class PropertySourceConfig {
}
