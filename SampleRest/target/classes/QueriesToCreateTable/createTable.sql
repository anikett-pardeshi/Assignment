CREATE TABLE IF NOT EXISTS movies (
    tconst varchar(255) primary key,
    titleType varchar(255),
    primaryTitle varchar(255),
    runtimeMinutes int,
    genres varchar(255)
);

CREATE TABLE IF NOT EXISTS ratings (
    tconst varchar(255) primary key,
    averageRating double,
    numVotes int
);