drop table if exists customers;
create table customers
(
	customerid int unsigned not null auto_increment primary key,
	name char(50) not null,
	address char(100) not null,
	city char(50) not null
);

drop table if exists orders;
create table orders
(
	orderid int unsigned not null auto_increment primary key,
	customerid int unsigned not null ,
	amount float(6,2),
	date date not null
);
drop table if exists books;
create table books
(
	isbn char(13) not null primary key,
	author char(50),
	title char(100),
	price float(4,2)
);

drop table if exists order_items;
create table order_items
(
	orderid int unsigned not null,
	isbn char(13) not null,
	quantity tinyint unsigned,
	primary key (orderid , isbn)
);

create table book_reviews
(
	isbn char(13) not null primary key,
	review text
);