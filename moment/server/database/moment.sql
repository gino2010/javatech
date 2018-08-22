create schema moment;

create table moment.t_image
(
  id      int auto_increment primary key,
  user_id varchar(10),
  image   MEDIUMBLOB        not null
);