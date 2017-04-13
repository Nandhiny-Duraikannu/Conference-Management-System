# --- !Ups
ALTER TABLE review ADD CONSTRAINT review__user_paper UNIQUE (user_id, paper_id);

# --- !Downs
DROP INDEX review__user_paper ON review;;