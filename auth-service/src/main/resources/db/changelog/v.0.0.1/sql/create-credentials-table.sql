CREATE TABLE credentials (
    user_id UUID,
    email VARCHAR(255) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    PRIMARY KEY(user_id)
);

COMMENT ON TABLE credentials IS 'Учетные данные';
COMMENT ON COLUMN credentials.user_id IS 'Идентификатор пользователя';
COMMENT ON COLUMN credentials.email IS 'Почта пользователя';
COMMENT ON COLUMN credentials.password IS 'Хэшированный пароль пользователя';