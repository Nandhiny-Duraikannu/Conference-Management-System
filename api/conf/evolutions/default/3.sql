# --- !Ups
INSERT INTO user (name, password, security_question, security_answer, email, title, research_areas, first_name, last_name, position, affiliation, phone, fax, address, city, country, zip, comments) VALUES ('anton', 'E10ADC3949BA59ABBE56E057F20F883E', '1', '1', 'anton.sergeyev@sv.cmu.edu', 'Mr', '', 'Anton', 'Sergeyev', '', '1991', '', '', '806 W Ahwanee Ave', 'Sunnyvale', '', '94085', '');
INSERT INTO user (name, password, security_question, security_answer, email, title, research_areas, first_name, last_name, position, affiliation, phone, fax, address, city, country, zip, comments) VALUES ('reviewer', 'E10ADC3949BA59ABBE56E057F20F883E', '1', '1', 'anton.sergeyev@sv.cmu.edu1', 'Mr', '', 'Anton', 'Sergeyev', '', '1991', '', '', '806 W Ahwanee Ave', 'Sunnyvale', '', '94085', '');
INSERT INTO conference (acronym, title, location, deadline, status) VALUES ('SOC', 'Service oriented computing conference', null, null, null);
INSERT INTO conference (acronym, title, location, deadline, status) VALUES ('IEEE', 'IEEE conference', null, null, null);
INSERT INTO paper (user_id, title, topic, contact_email, confirm_email, award_candidate, student_volunteer, status, paper_abstract, conference_id, file_format, file_size, submission_date, file_content) VALUES (1, 'paper 1 title', 'paper 1 topic', 'contact@email.com', 'contact@email.com', '1', '1', 'default', 'aasdf', 1, null, null, null, null);
INSERT INTO paper_authors (paper_id, type, author_first_name, author_last_name, author_affiliation, author_email) VALUES (1, '1', 'asdfaf', 'asdfasfd', 'sadfasdf', 'asdf@asdf.com');
INSERT INTO review (user_id, paper_id, content) VALUES (2, 1, null);
INSERT INTO review (user_id, paper_id, content) VALUES (1, 1, null);


# --- !Downs
delete from review;
delete from paper_authors;
delete from paper;
delete from conference;
delete from user;