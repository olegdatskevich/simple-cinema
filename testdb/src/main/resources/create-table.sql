DROP TABLE IF EXISTS movie;
CREATE TABLE movie (
 movieId INT NOT NULL AUTO_INCREMENT,
 movieName VARCHAR(255) NOT NULL,
 movieDescription VARCHAR(255) NULL,
 movieActive BOOLEAN,
 PRIMARY KEY (movieId),
 UNIQUE (movieName)
);
DROP TABLE IF EXISTS seance;
CREATE TABLE seance (
 seanceId INT NOT NULL AUTO_INCREMENT,
 seanceDate TIMESTAMP NOT NULL,
 seanceCost INT NOT NULL,
 seanceSold INT NULL,
 seanceActive BOOLEAN,
 movieId INT NOT NULL,
 PRIMARY KEY (seanceId),
 UNIQUE (seanceDate),
 FOREIGN KEY (movieId) REFERENCES movie (movieId)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION
);
