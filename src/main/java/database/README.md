# Database Utils

This web application uses the user 'robot' with password 'robot' to connect with a postgres database

In order to make the application work create the user
```postgresql
CREATE USER robot WITH PASSWORD 'robot' CREATEDB;
CREATE DATABASE robot OWNER robot;
```

This package contains some utils that can be used from command line

The class DataBaseUtils can:
+ Create the database using the commands in [CREATE_DATABASE.sql](../../database/CREATE_DATABASE.sql)
+ Create the tables using the commands in [CREATE_TABLE.sql](../../database/CREATE_TABLE.sql)
+ Seeds the database tables with values using commands in [SEED.sql](../../database/SEED.sql)
+ Insert some testing data into the database using the commands in [FAKE_DATA.sql](../../database/FAKE_DATA.sql)
+ Drop the whole database using the commands in [DROP.sql](../../database/DROP.sql)