CREATE TYPE operation_type AS ENUM ('EXPENSE', 'INCOME');

COMMENT ON TYPE operation_type IS 'Тип операции';

CREATE TABLE operations (
    operation_id UUID,
    user_id UUID NOT NULL,
    type operation_type NOT NULL,
    amount NUMERIC(14,2) NOT NULL,
    PRIMARY KEY (operation_id)
);

COMMENT ON TABLE operations IS 'Пользовательские операции';
COMMENT ON COLUMN operations.operation_id IS 'Идентификатор операции';
COMMENT ON COLUMN operations.user_id IS 'Идентификатор пользователя';
COMMENT ON COLUMN operations.type IS 'Тип операции';
COMMENT ON COLUMN operations.amount IS 'Сумма операции';