-- Таблица с постами
create table if not exists posts(
  id bigserial primary key,
  title varchar(256) not null,
  tags varchar(256) not null,
  text varchar(256) not null,
  imagePath varchar(256) not null,
  likesCount integer not null);

-- Таблица с комментариями
create table if not exists comments(
  id bigserial primary key,
  postId bigserial,
  text varchar(256) not null);