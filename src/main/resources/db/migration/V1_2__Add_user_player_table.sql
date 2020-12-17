CREATE TABLE user_player
(
    id             BIGSERIAL                 NOT NULL
        CONSTRAINT user_player_pkey PRIMARY KEY,
    user_id        BIGINT                    NOT NULL
        CONSTRAINT user_player_user_id_fkey REFERENCES fantasy_user,
    player_id      BIGINT                    NOT NULL
        CONSTRAINT user_player_player_id_fkey REFERENCES player,
    purchase_price FLOAT                     NOT NULL,
    purchased_at   TIMESTAMPTZ DEFAULT now() NOT NULL,
    CONSTRAINT user_player_user_id_player_id_unique UNIQUE (user_id, player_id)
);