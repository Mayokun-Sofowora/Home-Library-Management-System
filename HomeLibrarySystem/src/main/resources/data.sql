-- Inserting into author table
INSERT INTO author (name, country) VALUES ('Suzanne Collins', 'United States');
INSERT INTO author (name, country) VALUES ('Harper Lee', 'United States');
INSERT INTO author (name, country) VALUES ('Antoine de Saint-Exupéry', 'France');
INSERT INTO author (name, country) VALUES ('C.S. Lewis', 'Ireland');

-- Inserting into books table
INSERT INTO book (id, title, author, available_copies) VALUES (1, 'The Hunger Games', 'Suzanne Collins', 4);
INSERT INTO book (id, title, author, available_copies) VALUES (2, 'To Kill a Mockingbird', 'Harper Lee', 3);
INSERT INTO book (id, title, author, available_copies) VALUES (3, 'The Little Prince', 'Antoine de Saint-Exupéry', 6);
INSERT INTO book (id, title, author, available_copies) VALUES (4, 'The Chronicles of Narnia States', 'C.S. Lewis', 1);
