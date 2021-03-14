DROP TABLE Offer IF EXISTS;
DROP TABLE Client IF EXISTS;
DROP TABLE Credit IF EXISTS;

CREATE TABLE Client
(
    id        INT IDENTITY PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    telephone VARCHAR(15) UNIQUE,
    email     VARCHAR(255) UNIQUE,
    document  VARCHAR(10) UNIQUE
);

CREATE TABLE Credit
(
    id      INT IDENTITY PRIMARY KEY NOT NULL,
    name    VARCHAR(255)             NOT NULL,
    limit   int,
    percent int
);

CREATE TABLE Offer
(
    clientID int not null,
    creditID int not null,
    amount   int not null,t not null,

    CONSTRAINT CLIENT FOREIGN KEY (clientID) REFERENCES Client (id),
    CONSTRAINT CREDIT FOREIGN KEY (creditID) REFERENCES Credit (id)
);

INSERT INTO Client
    (name, telephone, email, document)
VALUES ('Виталий Евсеев', '88005553535', 'evseev.v@mail.ru', '2002123456'),
       ('Дмитрий Геращенко', '89990652517', 'gerashenko.d@mail.ru', '2002123457'),
       ('Иван Алексеев', '89991230987', 'noize@gmail.com', '2008555777'),
       ('Олег Савченко', '89201213473', 'oleg_sky@tut.by', '2012633712'),
       ('Дмитрий Кузнецов', '89510987654', 'husky@yandex.ru', '2017657231');

INSERT INTO Credit
    (name, limit, percent)
VALUES ('Автокредит', 3000000, 16),
       ('Ипотечный', 5000000, 8),
       ('Потребительский', 500000, 19);

INSERT INTO Offer
    (clientID, creditID, amount)
VALUES (0, 0, 1350000),
       (2, 1, 4250000),
       (4, 2, 425000);