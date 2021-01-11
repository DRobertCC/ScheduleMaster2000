SELECT username, schedulename, schedulelength, taskname, taskcontent
FROM users
    RIGHT JOIN (
        SELECT schedulename, schedulelength, tasks.*
        FROM tasks
            RIGHT JOIN (
                SELECT *
                FROM schedule_tasks
                    LEFT JOIN schedules
                        ON schedule_tasks.scheduleid = schedules.scheduleid) AS STS
                ON tasks.taskid = STS.taskid) AS TSTS
        ON users.userid = TSTS.userid;

-- Kiinduló változat: (from anorher project)
SELECT * FROM tasks RIGHT JOIN (SELECT * FROM tasks_schedules RIGHT JOIN schedules ON schedules.id=tasks_schedules.schedule_id) schedule ON tasks.id = schedule.task_id;

-- Átírva a miénkre:
SELECT schedulename, schedulelength, tasks.*
FROM tasks
    RIGHT JOIN (
    SELECT *
    FROM schedule_tasks
        LEFT JOIN schedules
            ON schedule_tasks.scheduleid = schedules.scheduleid) s
        ON tasks.taskid = s.taskid;

-- My starting ideas:
SELECT username, schedulename, schedulelength, taskname, taskcontent
FROM users AS u
         LEFT JOIN schedules s
                    ON u.userid = s.userid
         LEFT JOIN tasks t
                    ON u.userid = t.userid;

SELECT username, schedulename, schedulelength, taskname, taskcontent, day, startinghour, tasklength
FROM users AS u
    INNER JOIN schedules s
        ON u.userid = s.userid
    RIGHT JOIN tasks t
        ON u.userid = t.userid
    RIGHT JOIN schedule_tasks st
        ON s.scheduleid = st.scheduleid;