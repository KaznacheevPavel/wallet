package ru.kaznacheev.wallet.operation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;

/**
 * Сущность операции.
 */
@Entity
@Table(name = "operation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Operation {

    /**
     * Идентификатор операции.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private Long id;

    /**
     * Тип операции.
     * <p>
     * Возможные значения - {@link OperationType}
     */
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private OperationType type;

    /**
     * Сумма операции.
     */
    @Column(precision = 11, scale = 2)
    private BigDecimal amount;

    /**
     * Комментарий к операции.
     */
    private String comment;

}
