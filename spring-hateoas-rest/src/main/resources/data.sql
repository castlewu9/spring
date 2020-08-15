insert into users
    (bems, fname, lname, status, role)
values
    ('3137613', 'Sung', 'Park', 'Enabled', 'admin'),
    ('0000001', 'John', 'Doe', 'Enabled', 'user'),
    ('1234567', 'Woo', 'Park', 'Enabled', 'admin'),
    ('3829404', 'Jim', 'Smith', 'Disabled', 'admin'),
    ('1848593', 'Matt', 'Duke', 'Enabled', 'admin');

insert into alignments
    (id, name)
values
    (1, 'test'),
    (2, 'newmodel'),
    (3, 'old');

insert into points
    (id, x, y, z, align_id, pts_order)
values
    (1, 1, 2, 3, 1, 1),
    (2, 4, 5, 6, 1, 2),
    (3, 7, 8, 9, 1, 3),
    (4, -1, -2, -3, 2, 1),
    (5, 1.1, 1.2, 1.3, 2, 2),
    (6, 2.2, 2.2, 2.3, 2, 3),
    (7, 5.1, 2.3, 4.5, 3, 3),
    (8, 4.1, 2.3, 4.5, 3, 2),
    (9, 3, 2, 1, 3, 1);
