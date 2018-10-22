CREATE TABLE method_lock
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    method_name varchar(64) NOT NULL,
    update_time timestamp not null default current_timestamp on update current_timestamp
);
CREATE UNIQUE INDEX method_lock_method_name_uindex ON method_lock (method_name);