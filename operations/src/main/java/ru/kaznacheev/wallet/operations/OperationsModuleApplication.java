package ru.kaznacheev.wallet.operations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс для запуска приложения.
 */
@SpringBootApplication
public class OperationsModuleApplication {

    /**
     * Точка входа для запуска Spring Boot приложения.
     *
     * @param args Аргументы командной строки, переданные при запуске приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(OperationsModuleApplication.class, args);
    }

}
