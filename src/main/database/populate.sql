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