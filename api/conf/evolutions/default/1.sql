# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table conference (
  id                            bigint auto_increment not null,
  acronym                       varchar(255) not null,
  title                         varchar(255),
  location                      varchar(255),
  deadline                      datetime(6),
  status                        varchar(255),
  constraint pk_conference primary key (id)
);

create table paper (
  id                            bigint auto_increment not null,
  user_id                       bigint not null,
  title                         varchar(255) not null,
  topic                         varchar(255) not null,
  contact_email                 varchar(255) not null,
  confirm_email                 varchar(255),
  award_candidate               varchar(255) not null,
  student_volunteer             varchar(255) not null,
  status                        varchar(255) default 'new',
  paper_abstract                varchar(5000) not null,
  conference_id                 bigint,
  file_format                   varchar(255),
  file_size                     bigint,
  submission_date               varchar(255),
  file_content                  longblob,
  constraint pk_paper primary key (id)
);

create table paper_authors (
  id                            bigint auto_increment not null,
  paper_id                      bigint,
  type                          varchar(255) not null,
  author_first_name             varchar(255),
  author_last_name              varchar(255) not null,
  author_affiliation            varchar(255),
  author_email                  varchar(255),
  constraint pk_paper_authors primary key (id)
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

alter table paper add constraint fk_paper_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_paper_user_id on paper (user_id);

alter table paper add constraint fk_paper_conference_id foreign key (conference_id) references conference (id) on delete restrict on update restrict;
create index ix_paper_conference_id on paper (conference_id);

alter table paper_authors add constraint fk_paper_authors_paper_id foreign key (paper_id) references paper (id) on delete restrict on update restrict;
create index ix_paper_authors_paper_id on paper_authors (paper_id);


# --- !Downs

alter table paper drop foreign key fk_paper_user_id;
drop index ix_paper_user_id on paper;

alter table paper drop foreign key fk_paper_conference_id;
drop index ix_paper_conference_id on paper;

alter table paper_authors drop foreign key fk_paper_authors_paper_id;
drop index ix_paper_authors_paper_id on paper_authors;

drop table if exists conference;

drop table if exists paper;

drop table if exists paper_authors;

drop table if exists user;

