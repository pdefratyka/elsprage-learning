CREATE TABLE elsprage_learning.word_repetition
(
    id serial NOT NULL,
    learning_result_id integer NOT NULL,
    word_id integer NOT NULL,
    number_of_repetitions integer NOT NULL,
    PRIMARY KEY (id)
);