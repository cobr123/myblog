-- Таблица с постами
create table if not exists posts(
  id bigserial primary key,
  title varchar(256) not null,
  tags varchar(256) not null,
  text varchar(4096) not null,
  imagePath varchar(512) not null);

-- Таблица с комментариями
create table if not exists comments(
  id bigserial primary key,
  postId bigserial not null,
  text varchar(256) not null);

-- Таблица с лайками
create table if not exists likes(
  id bigserial primary key,
  postId bigserial not null,
  reaction boolean not null,
  dateTime TIMESTAMP not null);