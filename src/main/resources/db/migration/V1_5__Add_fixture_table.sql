CREATE TYPE FIXTURE_STATUS AS ENUM ('TBD', 'NS', '1H', 'HT', '2H', 'ET', 'P', 'FT', 'AET', 'PEN', 'BT', 'SUSP', 'INT', 'PST', 'CANC', 'ABD', 'AWD', 'WO');

CREATE TABLE fixture
(
    id          BIGSERIAL                 NOT NULL
        CONSTRAINT fixture_pkey PRIMARY KEY,
    external_id BIGINT                    NOT NULL,
    date        TIMESTAMPTZ               NOT NULL,
    round_id    BIGINT                    NOT NULL
        CONSTRAINT fixture_round_id_fkey REFERENCES round,
    home_team   BIGINT                    NOT NULL
        CONSTRAINT fixture_home_team_fkey REFERENCES team,
    away_team   BIGINT                    NOT NULL
        CONSTRAINT fixture_away_team_fkey REFERENCES team,
    home_goals  INT,
    away_goals  INT,
    status      FIXTURE_STATUS            NOT NULL,
    elapsed     INT,
    created_at  TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at  TIMESTAMPTZ,
    deleted_at  TIMESTAMPTZ,
    CONSTRAINT fixture_external_id_unique UNIQUE (external_id)
);

CREATE TABLE player_statistics
(
    id                BIGSERIAL                 NOT NULL
        CONSTRAINT player_statistics_pkey PRIMARY KEY,
    player_id         BIGINT                    NOT NULL
        CONSTRAINT player_statistics_player_id_fkey REFERENCES player,
    fixture_id        BIGINT                    NOT NULL
        CONSTRAINT player_statistics_fixture_id_fkey REFERENCES fixture,
    minutes_played    INT,
    rating            TEXT,
    offsides          INT,
    shots_total       INT,
    shots_on          INT,
    goals_total       INT,
    goals_conceded    INT,
    assists           INT,
    saves             INT,
    passes_total      INT,
    passes_key        INT,
    passes_accuracy   TEXT,
    tackles           INT,
    blocks            INT,
    interceptions     INT,
    duels_total       INT,
    duels_won         INT,
    dribbles_attempts INT,
    dribbles_success  INT,
    dribbles_past     INT,
    fouls_drawn       INT,
    fouls_committed   INT,
    cards_yellow      INT,
    cards_red         INT,
    penalty_scored    INT,
    penalty_missed    INT,
    penalty_saved     INT,
    created_at        TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at        TIMESTAMPTZ,
    deleted_at        TIMESTAMPTZ,
    CONSTRAINT player_statistics_player_id_fixture_id_unique UNIQUE (player_id, fixture_id)
);