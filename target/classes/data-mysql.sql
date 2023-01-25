INSERT INTO `city` (`postal_code`, `name`)
VALUES ('19370', 'Boljevac'),
       ('34000', 'Kragujevac'),
       ('11000', 'Beograd'),
       ('18000', 'Nis');

INSERT INTO `title` (professor_title)
VALUES ('Teaching asssistant'), ('Assistant professor'), ('Associate professor'), ('Professor');

INSERT INTO `users` (username, password, first_name, last_name, email, address, city)
VALUES ('kika', '$2a$10$yUU/QXOsxneIhYHXRMmw7Oj622GkF11y8Kwqj2rSKWW8dzoHJrzFe', 'Kristina', 'Trifunovic', 'kikatrifunovic00@icloud.com', 'Timocke divizije 15', '19370'),
       ('zora', '$2a$10$UHmFQ8J3aKxSglbadw3EK.g6gN3lVF96.fzijUMJ0VhAU0cJT8PsG', 'Zorica', 'Mihajlovic', 'zorica.m@gmail.com', 'Kralja Aleksandra 20', '34000'),
       ('dragan', '$2a$10$Lkc2erm4NQl7hFuU4ULWYu/.ue6lwRWP11AUB7xBSk0Glod6J/qCC', 'Dragan', 'Stojkovic', 'dragce@yahoo.com', 'Timocke bune 12', '19370'),
       ('nikolina', '$2a$10$KHGfS4YpYhscTQ.zvzn1pup1E97H288TTKCawI8C61GiuDEK1GSPe', 'Nikolina', 'Mihajlovic', 'nikolina.savic@icloud.com', 'Kralja Petra 3', '11000'),
       ('nikolina2', '$2a$10$KHGfS4YpYhscTQ.zvzn1pup1E97H288TTKCawI8C61GiuDEK1GSPe', 'Nikolina', 'Todorovic', 'nikolina@icloud.com', 'Kralja Aleksandra 19', '18000'),
       ('nikolina3', '$2a$10$KHGfS4YpYhscTQ.zvzn1pup1E97H288TTKCawI8C61GiuDEK1GSPe', 'Nikolina', 'Savic', 'nika@gmail.com', 'Milosa Petrovica 2/28', '11000'),
       ('marina', '$2a$10$JKhhH83jbIfKtYGcDF2NpuDOtFxp4BbjbGcpTm6qxQYZg55k/83qq', 'Marina', 'Radosavljevic', 'marina@gmail.com', 'Nikole Kopernika 33', '18000'),
       ('admin', '$2a$10$meDx.Ra7tqUpJiwD.eHMbO3kARC8LlVBHqNRGVJpNrHayvnZ/KFPi', 'Laza', 'Lazic', 'laza@gmail.com', 'Urosa Nikolica 14/4', '34000');

/*
 Credentials to use for users (username - password):
 professors:
    kika - kika
    marina - marina9891
 students:
    zora - zora
    dragan - dragan
    nikolina - niki
 admin:
    admin - admin
 */
INSERT INTO `student` (`index_number`, `index_year`, `current_year_of_study`, `username`)
VALUES  ('0180', '2017', '3', 'zora'),
        ('0190', '2015', '1', 'dragan'),
        ('0015', '2010', '5', 'nikolina'),
        ('0164', '2017', '3', 'nikolina2'),
        ('0185', '2017', '4', 'nikolina3');

INSERT INTO `professor` (`phone`, `reelection_date`, `title`, `username`)
VALUES ('0600800626', '2022-06-15 13:16:10', '2', 'kika'),
       ('0642288047', '2022-07-23', '1', 'marina');

INSERT INTO `subject` (description, name, no_of_esp, semester, year_of_study)
VALUES ('Osnove baza podataka, primarni i strani kljucevi, osnovni upiti', 'Uvod u baze podataka', '8', 'Winter', 1),
       ('Napredno-baze podataka. Strani kljucevi, slozeni upiti, trigeri, funkcije, procedure', 'Baze podataka', '8', 'Summer', 2),
       ('Detaljno objasnjavanje masine alatke (strug, busilica, glodalica, rendisaljka...)', 'Masine alatke', '6', 'Summer', 3),
       ('Vremenska i memorijska slozenost algoritama, detaljno se rade algoritmi Kruskalov algoritam, Dijkstra algoritam, BFS i DFS', 'Dizajn i analiza algoritama', '8', 'Winter', 2);

INSERT INTO `professor_teaches` (`professor`, `subject`)
VALUES ('kika', '1'),
       ('kika', '2'),
       ('marina', '3');

INSERT INTO `exam_period` (`name`, `start_date`, `end_date`, `active`)
VALUES ('December', '2021-12-21', '2021-12-26', null),
       ('January', '2022-01-25', '2022-02-13', null),
       ('April', '2022-04-05', '2022-04-10', null),
       ('June 1', '2022-06-02', '2022-06-19', null),
       ('June 2', '2022-06-23', '2022-07-10', null),
       ('September', '2022-08-23', '2022-09-04', null);

INSERT INTO `exam` (`exam_period`, `professor`, `subject`, `exam_date`, `grade`)
VALUES ('2', 'kika', '1', '2022-01-27', NULL),
       ('4', 'kika', '2', '2022-06-09', NULL),
       ('2', 'marina', '3', '2022-01-29', NULL);

INSERT INTO `student_takes_exams` (exam_period, professor, subject, student)
VALUES ('2', 'kika', '1', 'nikolina'),
       ('2', 'marina', '3', 'zora');

INSERT INTO `authorities` (authority, username)
VALUES ('ROLE_PROFESSOR', 'kika'),
       ('ROLE_PROFESSOR', 'marina'),
       ('ROLE_STUDENT', 'zora'),
       ('ROLE_STUDENT', 'dragan'),
       ('ROLE_STUDENT', 'nikolina'),
       ('ROLE_ADMIN', 'admin');

