# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table items (
  id                        bigint not null,
  user_name                 varchar(255),
  description               varchar(255),
  image_url                 varchar(255),
  date                      timestamp,
  constraint pk_items primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence items_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists items;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists items_seq;

drop sequence if exists user_seq;

