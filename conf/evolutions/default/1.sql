# --- !Ups
CREATE TABLE CarAdv (
  id         INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
  title      VARCHAR(255) NOT NULL,
  fual       VARCHAR(255) NOT NULL,
  price      INTEGER NOT NULL,
  newone        INTEGER NOT NULL,
  mileage    INTEGER,
  first_registration LONG
);

# --- !Downs
DROP TABLE CarAdv;