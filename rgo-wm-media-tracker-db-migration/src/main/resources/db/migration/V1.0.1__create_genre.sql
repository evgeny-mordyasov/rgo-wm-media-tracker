CREATE TABLE genre (
    PRIMARY KEY(uuid),
    uuid        UUID NOT NULL DEFAULT gen_random_uuid(),
    name        VARCHAR(256) NOT NULL,
    description VARCHAR(1024) NOT NULL
);

INSERT INTO genre (name, description)
VALUES ('Аниме', 'TO DO'),
       ('Мистика', 'TO DO'),
       ('Драма', 'TO DO');