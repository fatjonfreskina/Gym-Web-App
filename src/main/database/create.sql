-- run `cat create.sql | psql -U postgres`
DROP DATABASE IF EXISTS gwa_db; 
DROP SCHEMA IF EXISTS gwa CASCADE;
CREATE DATABASE gwa_db ENCODING = 'UTF8';
-- Connect to the new db using \c gwa_db

CREATE SCHEMA gwa;
SET search_path='gwa';

CREATE TYPE roles AS ENUM ('Trainee', 'Trainer', 'Secretary');

CREATE TABLE emailconfermation(
    person VARCHAR(40),
    token VARCHAR(256) NOT NULL,
    expirationdate DATE NOT NULL,
    PRIMARY KEY(person)
);

CREATE TABLE person (
	email VARCHAR(40),
	role roles[3],
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	psw VARCHAR(256) NOT NULL,
	taxcode CHAR(16) UNIQUE NOT NULL,
	birthdate DATE NOT NULL,
	telephone CHAR(10),
	address TEXT NOT NULL,
	avatarpath TEXT,
	PRIMARY KEY(email),
	CHECK(LENGTH(telephone) = 10),
	CHECK(date_part('year',age(current_date ,birthdate)) >= 14)
);

CREATE TABLE passwordreset (
	token VARCHAR(256),
	expirationdate TIMESTAMP NOT NULL,
	person VARCHAR(40),
	PRIMARY KEY(token)
);

CREATE TABLE medicalcertificate (
	person VARCHAR(40),
	expirationdate DATE,
	doctorname VARCHAR(30) NOT NULL,
	path TEXT NOT NULL,
	PRIMARY KEY(person,expirationdate)
);

CREATE TABLE reservation (
	trainee VARCHAR(40),
	lectureroom VARCHAR(30),
	lecturedate DATE,
	lecturestarttime TIME,
	PRIMARY KEY(trainee,lectureroom,lecturedate,lecturestarttime)
);

CREATE TABLE teaches (
	courseeditionid INTEGER,
	coursename VARCHAR(30) NOT NULL,
	trainer VARCHAR(40),
	PRIMARY KEY(courseeditionid,coursename,trainer)
);

CREATE TABLE room (
	name VARCHAR(30) NOT NULL,
	slots INTEGER NOT NULL,
	PRIMARY KEY(name)
);

CREATE TABLE lecturetimeslot (
	roomname VARCHAR(30),
	date DATE NOT NULL,
	starttime TIME NOT NULL,
	courseeditionid INTEGER,
	coursename VARCHAR(30) NOT NULL,
	substitution VARCHAR(40),
	PRIMARY KEY (roomname, date, starttime)
);

CREATE TABLE courseedition (
	id SERIAL,
	coursename VARCHAR(30) NOT NULL,
	PRIMARY KEY(id, coursename)
);

CREATE TABLE course (
	name VARCHAR(30) NOT NULL,
	description TEXT,
	PRIMARY KEY(name)
);

CREATE TABLE subscriptiontype (
	courseeditionid INTEGER,
	coursename VARCHAR(30) NOT NULL,
	duration INTEGER NOT NULL,
	cost DECIMAL(6,2) NOT NULL,	
	CHECK (duration > 0),
	CHECK (cost >= 0),
	PRIMARY KEY (courseeditionid, coursename, duration)
);

CREATE TABLE subscription (
	courseeditionid INTEGER,
	coursename VARCHAR(30) NOT NULL,
	duration INTEGER NOT NULL,
	startday DATE NOT NULL,
	trainee VARCHAR(40) NOT NULL,
	discount INTEGER,
	CHECK (discount >= 0 AND discount <= 100),
	PRIMARY KEY (courseeditionid, coursename, duration, startday, trainee)
);


--foreign keys
ALTER TABLE emailconfermation
ADD CONSTRAINT person_fk
FOREIGN KEY(person) REFERENCES person(email);

ALTER TABLE passwordreset 
ADD CONSTRAINT passwordreset_fk 
FOREIGN KEY(person) REFERENCES person(email);

ALTER TABLE medicalcertificate 
ADD CONSTRAINT medicalcertificate_fk 
FOREIGN KEY (person) REFERENCES person(email);

ALTER TABLE reservation 
ADD CONSTRAINT reservation_fk1 
FOREIGN KEY(trainee) REFERENCES person(email);

ALTER TABLE reservation 
ADD CONSTRAINT reservation_fk2 
FOREIGN KEY(lectureroom, lecturedate, lecturestarttime) REFERENCES lecturetimeslot(roomname, date, starttime);

ALTER TABLE teaches 
ADD CONSTRAINT teaches_fk1 
FOREIGN KEY(courseeditionid, coursename) REFERENCES courseedition(id, coursename);

ALTER TABLE teaches
ADD CONSTRAINT teaches_fk2
FOREIGN KEY(trainer) REFERENCES person(email);

ALTER TABLE lecturetimeslot 
ADD CONSTRAINT lecturetimeslot_fk1 
FOREIGN KEY(roomname) REFERENCES room(name);

ALTER TABLE lecturetimeslot 
ADD CONSTRAINT lecturetimeslot_fk2
FOREIGN KEY(courseeditionid, coursename) REFERENCES courseedition(id, coursename);

ALTER TABLE lecturetimeslot
ADD CONSTRAINT lecturetimeslot_fk3
FOREIGN KEY(substitution) REFERENCES person(email);

ALTER TABLE subscription
ADD CONSTRAINT subscription_fk1
FOREIGN KEY(courseeditionid, coursename, duration) REFERENCES subscriptiontype(courseeditionid, coursename, duration);

ALTER TABLE subscription
ADD CONSTRAINT subscription_fk2
FOREIGN KEY(trainee) REFERENCES person(email);

ALTER TABLE subscriptiontype
ADD CONSTRAINT subscriptiontype_fk
FOREIGN KEY(courseeditionid, coursename) REFERENCES courseedition(id, coursename);

ALTER TABLE courseedition
ADD CONSTRAINT courseedition_fk
FOREIGN KEY(coursename) REFERENCES course(name);
