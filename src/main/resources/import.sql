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

ALTER TABLE users
ALTER COLUMN id
RESTART WITH 2;