CREATE TABLE team
(
    id          BIGSERIAL                 NOT NULL
        CONSTRAINT team_pkey PRIMARY KEY,
    external_id BIGINT                    NOT NULL,
    team_name   TEXT                      NOT NULL,
    country     TEXT                      NOT NULL,
    founded     TEXT                      NOT NULL,
    logo        TEXT,
    created_at  TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at  TIMESTAMPTZ,
    deleted_at  TIMESTAMPTZ,
    CONSTRAINT team_external_id_unique UNIQUE (external_id)
);

CREATE TABLE player
(
    id          BIGSERIAL                 NOT NULL
        CONSTRAINT player_pkey PRIMARY KEY,
    external_id BIGINT                    NOT NULL,
    player_name TEXT                      NOT NULL,
    first_name  TEXT                      NOT NULL,
    last_name   TEXT                      NOT NULL,
    birthdate   DATE,
    nationality TEXT                      NOT NULL,
    cost        FLOAT       DEFAULT 0     NOT NULL,
    height      TEXT,
    weight      TEXT,
    injured     BOOLEAN     DEFAULT false NOT NULL,
    photo       TEXT,
    team_id     BIGINT
        CONSTRAINT player_team_id_fkey REFERENCES team,
    created_at  TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at  TIMESTAMPTZ,
    deleted_at  TIMESTAMPTZ,
    CONSTRAINT player_external_id_unique UNIQUE (external_id)
);
