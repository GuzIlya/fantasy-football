CREATE TABLE user_statistics
(
    id                  BIGSERIAL                 NOT NULL
        CONSTRAINT user_statistics_pkey PRIMARY KEY,
    user_id             BIGINT                    NOT NULL
        CONSTRAINT user_statistics_user_id_fkey REFERENCES fantasy_user,
    rank                BIGINT,
    prev_rank           BIGINT,
    pts                 FLOAT       DEFAULT 0     NOT NULL,
    prev_pts            FLOAT,
    rounds_participated INT,
    created_at          TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at          TIMESTAMPTZ,
    deleted_at          TIMESTAMPTZ,
    CONSTRAINT user_statistics_user_id_deleted_id_unique UNIQUE (user_id, deleted_at)
);