# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

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


# --- !Downs

drop table if exists user;

