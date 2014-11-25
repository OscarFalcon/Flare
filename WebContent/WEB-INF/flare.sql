CREATE DATABASE flare;

CREATE USER 'flare'@'localhost' IDENTIFIED BY 'flare-sql-password';
CREATE USER 'flare'@'%' IDENTIFIED BY 'flare-sql-password';
GRANT ALL ON *.* TO 'flare'@'localhost';
GRANT ALL ON *.* TO 'flare'@'%';

USE flare;


CREATE TABLE person
(
	user_id int unsigned primary key not null AUTO_INCREMENT,
	username varchar(32) UNIQUE not null,
	email varchar(64) UNIQUE not null,
	password varchar(32) not null,	
	first_name varchar(32) not null,
	last_name varchar(32) not null,
	profile_picture varchar(32),aboutme varchar(4096)
);



CREATE TABLE event
(
	id int unsigned primary key not null AUTO_INCREMENT,
	title varchar(32) not null,
	description varchar(1024) not null,
	locationLat DOUBLE(8,5),
	locationLong DOUBLE(8,5),
	date varchar(16),
	time varchar(16),
	user_id int unsigned,
	FOREIGN KEY(user_id) REFERENCES person(user_id)
);
 


CREATE TABLE friends
(	user_id int unsigned not null,
	friend_id int unsigned not null,
	FOREIGN KEY (user_id) REFERENCES person(user_id),
	FOREIGN KEY (friend_id) REFERENCES person(user_id)
);

CREATE TABLE attending_event
(
	user_id int unsigned not null,
	event_id int unsigned not null,
	FOREIGN KEY(user_id) REFERENCES person(user_id),
	 FOREIGN KEY(event_id) REFERENCES event(id)
);


 
