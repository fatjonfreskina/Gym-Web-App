--User
--MedialCertificate
--Teaches
--CourseEdition
--Course

INSERT INTO person VALUES (1,ARRAY['Trainee']::roles[],'Mario','Rossi','mario.rossi@example.com','d41d8cd98f00b204e9800998ecf8427e','RSSMRO0000000000','01/01/1970','3000000001','Address of Mario Rossi',null);
INSERT INTO person VALUES (2,ARRAY['Trainee']::roles[],'Riccardo','Bianchi','riccardo.bianchi@example.com','d41d8cd98f00b204e9800998ecf8427e','RCRBNC0000000000','01/02/1970','3000000002','Address of Riccardo Bianchi',null);
INSERT INTO person VALUES (3,ARRAY['Trainee']::roles[],'Luca','Giallo','luca.giallo@example.com','d41d8cd98f00b204e9800998ecf8427e','LCLGLL0000000000','01/03/1970','3000000003','Address of Luca Giallo',null);

INSERT INTO medicalcertificate VALUES (1,'01/01/2023','Doctor Sport', 'FAKE_PATH_FOR_FILE');
INSERT INTO medicalcertificate VALUES (2,'01/01/2023','Doctor Sport', 'FAKE_PATH_FOR_FILE');
INSERT INTO medicalcertificate VALUES (3,'01/01/2023','Doctor Sport', 'FAKE_PATH_FOR_FILE');




--Room
--LectureTimeSlot
--Reservation
--SubscriptionType
--Subscription



INSERT INTO room VALUES ('Stamina',30);
INSERT INTO room VALUES ('Energy',25);
INSERT INTO room VALUES ('Strength',15);
INSERT INTO room VALUES ('Power',15);





--Stamina
INSERT INTO lecturetimeslot ('Stamina', '2022-04-01', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-01', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-01', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-01', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Stamina', '2022-04-02', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-02', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-02', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-02', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Stamina', '2022-04-04', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-04', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-04', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-04', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Stamina', '2022-04-05', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-05', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-05', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-05', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Stamina', '2022-04-06', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-06', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-06', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-06', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Stamina', '2022-04-07', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-07', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-07', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-07', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Stamina', '2022-04-08', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-08', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-08', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Stamina', '2022-04-08', ' 18:00:00', courseID, courseName, NULL);

--Energy
INSERT INTO lecturetimeslot ('Energy', '2022-04-01', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-01', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-01', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-01', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Energy', '2022-04-02', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-02', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-02', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-02', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Energy', '2022-04-04', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-04', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-04', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-04', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Energy', '2022-04-05', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-05', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-05', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-05', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Energy', '2022-04-06', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-06', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-06', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-06', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Energy', '2022-04-07', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-07', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-07', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-07', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Energy', '2022-04-08', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-08', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-08', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Energy', '2022-04-08', ' 15:00:00', courseID, courseName, NULL);

--Strength
INSERT INTO lecturetimeslot ('Strength', '2022-04-01', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-01', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-01', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-01', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Strength', '2022-04-02', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-02', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-02', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-02', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Strength', '2022-04-04', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-04', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-04', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-04', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Strength', '2022-04-05', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-05', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-05', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-05', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Strength', '2022-04-06', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-06', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-06', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-06', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Strength', '2022-04-07', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-07', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-07', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-07', ' 18:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Strength', '2022-04-08', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-08', ' 15:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-08', ' 17:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Strength', '2022-04-08', ' 18:00:00', courseID, courseName, NULL);

--Power
INSERT INTO lecturetimeslot ('Power', '2022-04-01', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-01', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-01', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-01', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Power', '2022-04-02', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-02', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-02', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-02', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Power', '2022-04-04', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-04', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-04', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-04', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Power', '2022-04-05', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-05', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-05', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-05', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Power', '2022-04-06', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-06', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-06', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-06', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Power', '2022-04-07', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-07', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-07', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-07', ' 15:00:00', courseID, courseName, NULL);

INSERT INTO lecturetimeslot ('Power', '2022-04-08', ' 10:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-08', ' 11:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-08', ' 14:00:00', courseID, courseName, NULL);
INSERT INTO lecturetimeslot ('Power', '2022-04-08', ' 15:00:00', courseID, courseName, NULL);





--User1
INSERT INTO reservation (1, 'Stamina', '2022-04-01', ' 14:00:00');
INSERT INTO reservation (1, 'Stamina', '2022-04-02', ' 14:00:00');
INSERT INTO reservation (1, 'Stamina', '2022-04-05', ' 14:00:00');
INSERT INTO reservation (1, 'Stamina', '2022-04-08', ' 15:00:00');

--User2
INSERT INTO reservation (2, 'Energy', '2022-04-01', ' 10:00:00');
INSERT INTO reservation (2, 'Energy', '2022-04-04', ' 14:00:00');





--course1
INSERT INTO subscriptiontype(courseID, courseName, 1, 40.0, 0, NULL);
INSERT INTO subscriptiontype(courseID, courseName, 2, 70.0, 0, NULL);
INSERT INTO subscriptiontype(courseID, courseName, 3, 100.0, 0, NULL);
INSERT INTO subscriptiontype(courseID, courseName, 6, 160.0, 0, NULL);
INSERT INTO subscriptiontype(courseID, courseName, 12, 250.0, 0, NULL);

--course2
INSERT INTO subscriptiontype(courseID, courseName, 1, 30.0, 0, NULL);
INSERT INTO subscriptiontype(courseID, courseName, 2, 55.0, 0, NULL);
INSERT INTO subscriptiontype(courseID, courseName, 3, 80.0, 0, NULL);
INSERT INTO subscriptiontype(courseID, courseName, 6, 150.0, 0, NULL);
INSERT INTO subscriptiontype(courseID, courseName, 12, 220.0, 0, NULL);


--subscriptions
INSERT INTO subscription(courseID, courseName, 1, '2022-03-27', 1);
INSERT INTO subscription(courseID, courseName, 3, '2022-03-02', 2);
INSERT INTO subscription(courseID, courseName, 12, '2022-01-08', 2);