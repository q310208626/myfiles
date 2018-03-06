--create user
create user 'mysql'@'%' identified by 'mysql';

--grant privileges
grant all on 'mysql'@'%' on myfiles;

--create database
create database if not exists myfiles default character uft8;