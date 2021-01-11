DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS schedules CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS schedule_tasks CASCADE;

CREATE TABLE users (
   userid SMALLSERIAL PRIMARY KEY,
   username VARCHAR(100),
   userpassword VARCHAR,
   isadmin BOOLEAN DEFAULT FALSE
);

CREATE TABLE schedules (
   scheduleid SMALLSERIAL PRIMARY KEY,
   schedulename VARCHAR(30),
   schedulelength SMALLINT,
   ispublic BOOLEAN,
   userid SMALLINT,
   FOREIGN KEY (userid) REFERENCES users (userid),
   CONSTRAINT scheduleChecker UNIQUE (schedulename, schedulelength, ispublic, userid)
);

CREATE TABLE tasks (
   taskid SMALLSERIAL PRIMARY KEY,
   taskname VARCHAR(30),
   taskcontent VARCHAR(30),
   userid INT,
   FOREIGN KEY (userid) REFERENCES users (userid),
   CONSTRAINT taskChecker UNIQUE (taskname, taskcontent, userid)
);

CREATE TABLE schedule_tasks (
    userid SMALLINT,
    scheduleid SMALLINT,
    taskid SMALLINT,
    day SMALLINT,
    startinghour SMALLINT,
    tasklength SMALLINT,
    FOREIGN KEY (taskid) REFERENCES tasks (taskid),
    FOREIGN KEY (userid) REFERENCES users (userid),
    FOREIGN KEY (scheduleid) REFERENCES schedules (scheduleid)
);

INSERT INTO users(username, userpassword, isAdmin) VALUES
    ('admin', '1234', true),
    ('asdf', '1234', false),
    ('robi', '1234', false),
    ('Code Cool', 'eyJhbGciOiJSUzI1NiIsImtpZCI6IjM0OTRiMWU3ODZjZGFkMDkyZTQyMzc2NmJiZTM3ZjU0ZWQ4N2IyMmQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiMTgxMjk0MTc3MTktcm50bDFsM3M1YzZkOHNvcWlwN3IybnVnbjQ4aG5jOWwuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxODEyOTQxNzcxOS1ybnRsMWwzczVjNmQ4c29xaXA3cjJudWduNDhobmM5bC5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExMzA2MDA0NTg2OTg1NTM1MTk4MSIsImVtYWlsIjoiZDN2dDNzdDFuNkBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6IjAwMFdpZGdPM084QVd1WTI0Y0lMZ2ciLCJuYW1lIjoiQ29kZSBDb29sIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS8tRXlVRnIxcTRMaDQvQUFBQUFBQUFBQUkvQUFBQUFBQUFBQUEvQUNIaTNyZE1rTUdjZ0FmR3k1aVJab3FvRi13RXNla0RMQS9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiQ29kZSIsImZhbWlseV9uYW1lIjoiQ29vbCIsImxvY2FsZSI6ImVuLUdCIiwiaWF0IjoxNTY0NjY2MDMyLCJleHAiOjE1NjQ2Njk2MzIsImp0aSI6ImQ0YjFlNTc5ZjEzZDMwZTU4NjAyZmFhMjljYWE1YjE0Y2I3NzA5NGYifQ.t1Up9Zyq0spo5DrBcZKcxzvLrVRbAwUD9GdSikN4zpRLK84GZS7ZLZ1FxRuxMBKQFNYNl27LCK92wYOiMK_jRKX_4eBYbjplzxDEO1e0BvsP0Qy7mtBxw3Q6xZlfUIZXPivXcj3bHpBT3W3oZBuzg5VKkAAkWpZS9lW0GwiMoJseo1bfoiy031cX4Q7nnLWi0cjuBJwZgmsdS2Jnj7WmAR9QpAiyW4e1-cROwaxMzNvr8YaFwEf3v7a99k1bZYBaee_f5ITK2KwCdkIVSDfFVED-2CsAgp0nR8GcVhEJoC9EHs3vYMM_X2sMBfEnvn0AGuIClPQ3nQeP9v88dvyJug', false);

INSERT INTO schedules(schedulename, schedulelength, ispublic, userid) VALUES
    ('Exercise', 6, true, 2),
    ('School', 5, true, 2),
    ('Todo', 5, false, 2),
    ('Todo', 5, false, 3),
    ('Todo', 5, false, 1),
    ('Weekday Workout', 5, true, 3),
    ('Workout', 7, true, 4),
    ('Weekend', 2, true, 4),
    ('School', 5, true, 4);

INSERT INTO tasks(taskname, taskcontent, userid) VALUES
    ('Posta', 'Elmenni a levelekért', 2),
    ('Math', 'Tanóra', 2),
    ('Science', 'Tanóra', 2),
    ('Leg', 'Workout', 3),
    ('Chest', 'Workout', 3),
    ('Google Sign-in', 'Add Google Sign-in to SM2000', 1),
    ('Testing', 'Test and Bugfix the app', 1);

INSERT INTO schedule_tasks(userid, scheduleid, taskid, day, startinghour, tasklength) VALUES
    (1, 5, 6, 1, 9, 6),
    (1, 5, 6, 2, 9, 6),
    (1, 5, 7, 3, 9, 6),
    (1, 5, 7, 4, 9, 6),
    (2, 2, 2, 3, 8, 2),
    (2, 2, 3, 3, 10, 2);
