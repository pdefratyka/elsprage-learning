CREATE TABLE elsprage_learning.game_result
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    packet_id integer NOT NULL,
    date TIMESTAMPTZ NOT NULL,
    score NUMERIC(5, 2) NOT NULL,
    game_type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);