# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table paper (
  id                            bigint auto_increment not null,
  title                         varchar(255),
  topic                         varchar(255),
  contact_email                 varchar(255),
  award_candidate               varchar(255),
  student_volunteer             varchar(255),
  status                        varchar(255),
  paper_abstract                varchar(5000),
  conference_id                 varchar(255),
  file_format                   varchar(255),
  file_size                     varchar(255),
  submission_date               varchar(255),
  constraint pk_paper primary key (id)
);

create table paper_authors (
  paper_id                      bigint,
  author_id                     bigint,
  type                          varchar(255)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  password                      varchar(255),
  security_question             varchar(255),
  security_answer               varchar(255),
  email                         varchar(255),
  title                         varchar(255),
  research_areas                varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  position                      varchar(255),
  affiliation                   varchar(255),
  phone                         varchar(255),
  fax                           varchar(255),
  address                       varchar(255),
  city                          varchar(255),
  country                       varchar(255),
  zip                           varchar(255),
  comments                      varchar(5000),
  constraint uq_user_name unique (name),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);

alter table paper_authors add constraint fk_paper_authors_paper_id foreign key (paper_id) references paper (id) on delete restrict on update restrict;
create index ix_paper_authors_paper_id on paper_authors (paper_id);

alter table paper_authors add constraint fk_paper_authors_author_id foreign key (author_id) references user (id) on delete restrict on update restrict;
create index ix_paper_authors_author_id on paper_authors (author_id);


# --- !Downs

alter table paper_authors drop foreign key fk_paper_authors_paper_id;
drop index ix_paper_authors_paper_id on paper_authors;

alter table paper_authors drop foreign key fk_paper_authors_author_id;
drop index ix_paper_authors_author_id on paper_authors;

drop table if exists paper;

drop table if exists paper_authors;

drop table if exists user;

