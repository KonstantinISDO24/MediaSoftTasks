CREATE TABLE match (
    ID SERIAL PRIMARY KEY,
    season VARCHAR(50) NOT NULL,
    match_date TIMESTAMP NOT NULL,
    home_team VARCHAR(255) NOT NULL,
    away_team VARCHAR(255) NOT NULL,
    home_score INT,
    away_score INT
);