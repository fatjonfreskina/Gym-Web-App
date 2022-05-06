# GWA - Gym Web App

![Alt text](./src/main/webapp/images/GWA_logo.svg?raw=true)

## Group members
| Surname      | Name          | ID            |
| ------------ | ------------- | ------------- |
| Alessio      | Marco         | 1242412       |
| Caldivezzi   | Francesco     | 2037893       |
| Campeol      | Alberto       | 2064439       |
| D'Antimo      | Simone        | 2052413       |
| Forzan       | Riccardo      | 2057453       |
| Freskina     | Fatjon        | 2056098       |
| Pasin        | Andrea        | 2041605       |
| Singh        | Harjot        | 2053081       |
| Tumiati      | Riccardo      | 2054534       |


## Overview
* [General description of the web-app](#general-description-of-the-web-app)
* [Users of the web app](#users-of-the-web-app)
* [Necessary steps before usage](#necessary-steps-before-usage)

## General description of the web-app
This web application is designed to be used for the management of a gym. It can be used by both the users attending the various courses offered by the gym and by the trainers/secretaries.<br />
The aim of this web application is to simplify the users' tasks, allowing to manage courses, attendances and subscriptions with only a few clicks.

## Users of the web-app
This web application has been implemented for different users, each one of them having different functionalities available.
### Guest
The Guest represents a user who accesses the web application without logging in. He will be able to have a general insight of the gym.
### Trainee
The Trainee represents a logged-in user that attends courses in the gym. Each Trainee can manage his/her personal information and books lectures for the courses he is subscribed to.
### Trainer
The Trainer represents a logged-in user who teaches one or more courses. Each Trainer can manage his/her personal information and manage the attendances for the courses he/she teaches.
### Secretary
The Secretary represents a logged-in user who takes care of the correct and timely management of secretarial activities regarding the gym.

## Necessary steps before usage
To make this webapp working correctly you need to :
- create a folder inside the tomcat web server installation folder called `gwa`
- then go inside gwa and create two other subdirectories called : `avatars` and `medical_certificates`

Before using this webapp you have to create and eventually populate the database.<br />
You can follow the instructions in `src/main/java/database/README.md`.<br />
We have also created a Java class with path `src/main/java/database/DataBaseUtils.java` which allows you to create and populate your local database easily.
 
