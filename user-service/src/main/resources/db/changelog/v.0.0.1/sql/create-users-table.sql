CREATE TABLE users (
    user_id BIGSERIAL,
    username VARCHAR(20) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    PRIMARY KEY(user_id)
);

COMMENT ON TABLE users IS 'Пользователи';
COMMENT ON COLUMN users.user_id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.username IS 'Уникальное имя пользователя';
COMMENT ON COLUMN users.password IS 'Хэшированный пароль пользователя';