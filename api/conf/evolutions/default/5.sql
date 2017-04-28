# --- !Ups
ALTER TABLE user ADD COLUMN role VARCHAR(255) default "user";

# --- !Downs
ALTER TABLE user DROP COLUMN role;