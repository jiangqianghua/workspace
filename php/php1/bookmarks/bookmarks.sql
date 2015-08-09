/*create database bookmarks;*/
use bookmarks;

DROP TABLE IF EXISTS users;
create table users(
	username varchar(16) not null primary key,
	passwd char(40) not null,
	email varchar(100) not null
);

DROP TABLE IF EXISTS bookmark;
create table bookmark(
	username varchar(16) not null,
	bm_URL varchar(255) not null,
	index(username),
	index(bm_URL),
	primary key(username , bm_URL)
);


