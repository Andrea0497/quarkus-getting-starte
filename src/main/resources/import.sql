INSERT INTO
    users (
        ID,
        created_at,
        updated_at,
        first_name,
        last_name,
        email
    )
VALUES
    (
        1,
        NOW (),
        NOW (),
        'Andrea',
        'Capriello',
        'andrea.capriello@example.com'
    );

INSERT INTO
    users (
        ID,
        created_at,
        updated_at,
        first_name,
        last_name,
        email
    )
VALUES
    (
        2,
        NOW (),
        NOW (),
        'Mario',
        'Rossi',
        'mario.rossi@example.com'
    );

INSERT INTO
    roles (id, definition)
VALUES
    (1, 'admin');

INSERT INTO
    roles (id, definition)
VALUES
    (2, 'simply');

INSERT INTO
    users_roles (user_ID, role_ID)
VALUES
    (1, 1);

INSERT INTO
    users_roles (user_ID, role_ID)
VALUES
    (2, 2);

ALTER TABLE users
ALTER COLUMN id
RESTART WITH 3;

ALTER TABLE roles
ALTER COLUMN id
RESTART WITH 3;