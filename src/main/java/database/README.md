# Database Utils

This web application uses the user 'robot' with password 'robot' to connect with a postgres database

In order to make the application work create the user
```postgresql
CREATE USER robot WITH PASSWORD 'robot' CREATEDB;
CREATE DATABASE robot OWNER robot;
```

This package contains some utils that can be used from command line

The class DataBaseUtils can:
* Create the database using the file [create.sql](../../database/create.sql)
* Insert some testing data into the database using the file [populate.sql](../../database/populate.sql)
* Drop the whole database