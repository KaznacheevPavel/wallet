CREATE TYPE operation_type AS ENUM ('INCOME', 'EXPENSE');

CREATE TABLE operation (
    operation_id BIGSERIAL NOT NULL UNIQUE,
    type operation_type NOT NULL,
    amount NUMERIC(11, 2) NOT NULL,
    comment VARCHAR(255),
    PRIMARY KEY (operation_id)
);
