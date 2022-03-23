DROP DATABASE IF EXISTS gwa;


//FINTO 
CREATE TABLE IF NOT EXISTS passwordreset (
	token BIGINT,
	expirationdate TIMESTAMP NOT NULL,
	user INTEGER,
	PRIMARY KEY(token),
	FOREIGN KEY(user) REFERENCES user(id)
);

//ALMOST OK
CREATE TABLE IF NOT EXISTS user (
	id SERIAL,
	role roles[3],
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	email VARCHAR(40) NOT NULL,
	psw BIGINT NOT NULL,
	taxcode CHAR(16) UNIQUE NOT NULL,
	birthdate DATE NOT NULL,
	telephone CHAR(10),
	address TEXT NOT NULL,
	avatarpath TEXT,
	PRIMARY KEY(id),
	CHECK(LENGTH(telephone) == 9)
);

CREATE TYPE IF NOT EXISTS roles AS ENUM ('Trainee', 'Trainer', 'Secretary');


//FINITO
CREATE TABLE IF NOT EXISTS medicalcertificate (
	user BIGINT,
	expirationdate DATE,
	doctorname TEXT NOT NULL,
	path TEXT NOT NULL,
	PRIMARY KEY(user,expirationdate),
	FOREIGN KEY(user) REFERENCES user(id)
);

//FINITO
CREATE TABLE IF NOT EXISTS reservation (
	trainee INTEGER,
	lectureroom VARCHAR(30),
	lecturedate DATE,
	lecturestarttime TIME,
	PRIMARY KEY(trainee,lectureroom,lecturedate,lecturestarttime),
	FOREIGN KEY(trainee) REFERENCES user(id),
	FOREIGN KEY(lectureroom,lecturedate,lecturestarttime) REFERENCES lecturetimeslot(roomname,date,starttime)
);

//OK
CREATE TABLE IF NOT EXISTS teaches (
	courseeditionid INTEGER,
	coursename VARCHAR(30),
	trainer INTEGER,
	PRIMARY KEY(courseeditionid,coursename,trainer),
	FOREIGN KEY(courseeditionid,coursename) REFERENCES courseedition(id,coursename)

);

//OK
CREATE TABLE IF NOT EXISTS room (
	name VARCHAR(30),
	slots INTEGER NOT NULL,
	PRIMARY KEY(name)
);
