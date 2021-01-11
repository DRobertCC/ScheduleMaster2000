# ScheduleMaster2000

In this AJAX (Single-page, JavaScript) JDBC web-application users can create multiple schedules for themselves in which they can create tasks which can contain text or pictures sharing it with others as they like!

## `DataSource`

Before deploying to a webserver create a `Resource` in the webserver's config (e.g. for Apache Tomcat in `conf/context.xml`).


```
<Resource name="jdbc/schedulemaster2000"
          type="javax.sql.DataSource"
          username="postgres"
          password="admin"
          driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://localhost:5432/schedulemaster2000"
          closeMethod="close"/>
```

## About

This app was written by a Codecooler, Róbert Dancsó from Miskolc.
