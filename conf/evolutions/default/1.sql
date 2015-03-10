# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint not null,
  user_name                 varchar(255),
  password                  varchar(255),
  type                      integer,
  create_date               timestamp,
  constraint pk_account primary key (id))
;

create table items (
  id                        bigint not null,
  user_name                 varchar(255),
  description               varchar(255),
  image_url                 varchar(255),
  date                      timestamp,
  constraint pk_items primary key (id))
;

create sequence account_seq;

create sequence items_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists items;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists items_seq;

