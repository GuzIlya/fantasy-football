CREATE TYPE ROUND_STATUS AS ENUM ('UPCOMING', 'OPEN', 'CLOSED', 'FINISHED', 'PROCESSED');

CREATE TABLE round
(
    id         BIGSERIAL                                             NOT NULL
        CONSTRAINT round_pkey PRIMARY KEY,
    name       TEXT                                                  NOT NULL,
    status     ROUND_STATUS DEFAULT CAST('UPCOMING' AS ROUND_STATUS) NOT NULL,
    created_at TIMESTAMPTZ  DEFAULT now()                            NOT NULL,
    updated_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ,
    CONSTRAINT name_unique UNIQUE (name)
);

CREATE TABLE lineup
(
    id         BIGSERIAL                 NOT NULL
        CONSTRAINT lineup_pkey PRIMARY KEY,
    user_id    BIGINT                    NOT NULL
        CONSTRAINT lineup_user_id_fkey REFERENCES fantasy_user,
    round_id   BIGINT                    NOT NULL
        CONSTRAINT lineup_round_id_fkey REFERENCES round,
    created_at TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ,
    CONSTRAINT lineup_user_id_round_id_unique UNIQUE (user_id, round_id)
);

CREATE TABLE lineup_player
(
    id        BIGSERIAL                 NOT NULL
        CONSTRAINT lineup_player_pkey PRIMARY KEY,
    number    INT                       NOT NULL,
    lineup_id BIGINT                    NOT NULL
        CONSTRAINT lineup_player_lineup_id_fkey REFERENCES lineup,
    player_id BIGINT                    NOT NULL
        CONSTRAINT lineup_player_player_id_fkey REFERENCES player,
    added_at  TIMESTAMPTZ DEFAULT now() NOT NULL,
    CONSTRAINT lineup_player_lineup_id_player_id_unique UNIQUE (lineup_id, player_id),
    CONSTRAINT lineup_player_lineup_id_number_unique UNIQUE (lineup_id, number)
);