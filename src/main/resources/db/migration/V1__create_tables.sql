CREATE TABLE elsprage_learning.learning_result
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    packet_id integer NOT NULL,
    date TIMESTAMPTZ NOT NULL,
    score NUMERIC(5, 2) NOT NULL,
    PRIMARY KEY (id)
);