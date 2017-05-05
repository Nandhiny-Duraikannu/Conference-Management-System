# --- !Ups
ALTER TABLE review ADD COLUMN status VARCHAR(16) default null;
ALTER TABLE pcmember drop index pcmember__user_conference;
ALTER TABLE pcmember ADD CONSTRAINT pcmember__user_conference UNIQUE (user_id, conference_id, role);
# --- !Downs
ALTER TABLE review DROP COLUMN status;