CREATE EXTENSION IF NOT EXISTS "citext";

CREATE TYPE USER_ROLE AS ENUM ('USER', 'ADMIN');

CREATE TABLE fantasy_user
(
    id            BIGSERIAL                 NOT NULL
        CONSTRAINT user_pkey PRIMARY KEY,
    username      TEXT                      NOT NULL,
    email         CITEXT                    NOT NULL,
    role          USER_ROLE                 NOT NULL,
    cash          FLOAT                     NOT NULL,
    password_hash TEXT                      NOT NULL,
    created_at    TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at    TIMESTAMPTZ,
    deleted_at    TIMESTAMPTZ
);

-- Password: Admin
INSERT INTO fantasy_user(username, email, role, cash, password_hash)
VALUES ('Admin', 'admin@domain.com', 'ADMIN', 100,
        '$2a$10$4yAqcjeyVG/1Lqg/W1uwNuO6CsP7f4cnZyqF7sOZ1/TS9zCPipzdm')