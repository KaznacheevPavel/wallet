package ru.kaznacheev.wallet.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:constraint/constraint.yaml", factory = YamlPropertySourceFactory.class)
public class PropertySourceConfig {

}
