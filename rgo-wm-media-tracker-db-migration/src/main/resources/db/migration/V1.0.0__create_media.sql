CREATE TABLE media (
    PRIMARY KEY(uuid),
    uuid UUID NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(256) NOT NULL,
    year SMALLINT NOT NULL CHECK (year > 1894)
);
