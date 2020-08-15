create table users
(
    bems VARCHAR(255),
    fname VARCHAR(255),
    lname VARCHAR(255),
    status VARCHAR(255),
    role VARCHAR(255),
    PRIMARY KEY (bems)
);

create table alignments
(
    id INTEGER,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

create table points
(
    id INTEGER,
    x REAL,
    y REAL,
    z REAL,
    align_id INTEGER,
    pts_order INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (align_id) REFERENCES alignments(id)
);
