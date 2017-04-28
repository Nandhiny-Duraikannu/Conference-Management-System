# --- !Ups
ALTER TABLE conference ADD COLUMN logo VARCHAR(255) default null;
ALTER TABLE conference ADD COLUMN submission_date_start DATETIME default null;

# --- !Downs
ALTER TABLE conference DROP COLUMN logo;
ALTER TABLE conference DROP COLUMN submission_date_start;