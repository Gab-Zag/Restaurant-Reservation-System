CREATE TABLE IF NOT EXISTS users(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS tables(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100),
    capacity INT,
    status VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS reservation(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT,
    table_id INT,
    reservation_data TIMESTAMP,
    status VARCHAR(25),

    CONSTRAINT fk_reservation_client
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE CASCADE,

    CONSTRAINT fk_reservation_table
    FOREIGN KEY (table_id)
    REFERENCES tables (id)
    ON DELETE CASCADE
);