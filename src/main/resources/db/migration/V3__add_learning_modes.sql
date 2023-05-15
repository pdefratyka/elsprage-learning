ALTER TABLE elsprage_learning.learning_result ADD COLUMN learning_mode VARCHAR(255);
ALTER TABLE elsprage_learning.learning_result ADD COLUMN is_repetition BOOLEAN;

UPDATE elsprage_learning.learning_result SET learning_mode = 'TRANSLATION_TO_VALUE', is_repetition = FALSE;