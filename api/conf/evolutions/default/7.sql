# --- !Ups
create table review_question (
  id                            bigint auto_increment not null,
  conference_id                 bigint not null,
  question                      varchar(500),
  is_public                     varchar(256),
  position1                     varchar(500),
  choice1                       varchar(500),
  position2                     varchar(500),
  choice2                       varchar(500),
  position3                     varchar(500),
  choice3                       varchar(500),
  position4                     varchar(500),
  choice4                       varchar(500),
  constraint rq_question primary key (id)
);

alter table review_question add constraint fk_rqmember_conference_id foreign key (conference_id) references conference (id) on delete restrict on update restrict;
create index ix_pcmember_conference_id on review_question (conference_id);

# --- !Down