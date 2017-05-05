# --- !Ups
ALTER TABLE review ADD COLUMN status VARCHAR(16) default null;

# --- !Downs
ALTER TABLE review DROP COLUMN status;