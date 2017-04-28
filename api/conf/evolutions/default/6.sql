# --- !Ups
create table pcmember (
  id                            bigint auto_increment not null,
  conference_id                 bigint not null,
  user_id                       bigint not null,
  role                          varchar(16),
  constraint pk_pcmember primary key (id)
);

create table email_template (
  id                            bigint auto_increment not null,
  conference_id                 bigint not null,
  title                         varchar(128),
  content                       varchar(5000),
  constraint pk_email_template primary key (id)
);

alter table pcmember add constraint fk_pcmember_conference_id foreign key (conference_id) references conference (id) on delete restrict on update restrict;
create index ix_pcmember_conference_id on pcmember (conference_id);

alter table pcmember add constraint fk_pcmember_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_pcmember_user_id on pcmember (user_id);

alter table email_template add constraint fk_email_template_conference_id foreign key (conference_id) references conference (id) on delete restrict on update restrict;
create index ix_email_template_conference_id on email_template (conference_id);

ALTER TABLE pcmember ADD CONSTRAINT pcmember__user_conference UNIQUE (user_id, conference_id);

# --- !Down
