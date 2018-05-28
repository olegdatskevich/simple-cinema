DROP TABLE IF EXISTS movie CASCADE;
CREATE TABLE movie (
 movieId serial PRIMARY KEY,
 movieName VARCHAR(255) UNIQUE NOT NULL,
 movieDescription VARCHAR(255) NULL,
 movieActive BOOLEAN
);
DROP TABLE IF EXISTS seance CASCADE;
CREATE TABLE seance (
 seanceId serial PRIMARY KEY,
 seanceDate TIMESTAMP UNIQUE NOT NULL,
 seanceCost INT NOT NULL,
 seanceSold INT NULL,
 seanceActive BOOLEAN,
 movieId INT NOT NULL,
 CONSTRAINT seance_movieId_fkey FOREIGN KEY (movieId)
 REFERENCES movie (movieId)
 ON DELETE NO ACTION ON UPDATE NO ACTION
);